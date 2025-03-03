package com.example.shoplist.presentation.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LongDef
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.shopItem.MAX_POOL_SIZE
import com.example.shoplist.presentation.shopItem.SHOP_LIST_DISABLED
import com.example.shoplist.presentation.shopItem.SHOP_LIST_ENABLED
import com.example.shoplist.presentation.shopItem.ShopItemActivity
import com.example.shoplist.presentation.shopItem.ShopItemFragment
import com.example.shoplist.presentation.shopItem.ShopListAdapter
import com.example.shoplist.presentation.ShopListApp
import com.example.shoplist.presentation.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShopItemFragment.onEditingFinished {
    private lateinit var shopListAdapter: ShopListAdapter

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as ShopListApp).component
    }

    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setRecyclerView()

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.mainViewModelState.observe(this) { state ->
            shopListAdapter.submitList(state)
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddShopItemFragment())
            }
        }

        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.shoplist/shopItems"),
                // Адрес куда отправляем запрос content + authorities / название таблицы
                null,
                null,
                null,
                null,
                null
            )

            while (cursor?.moveToNext() == true) {
                Log.d("MainActivity", "Cursor: ${cursor.columnCount}")
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enable = cursor.getInt(cursor.getColumnIndexOrThrow("enable")) > 0

                val shopItem = ShopItem(name, count, enable, id)

                Log.d("MainActivity", shopItem.toString())
            }

            cursor?.close()
        }


    }

    override fun onEditingFinished() {
        Toast.makeText(
            this@MainActivity,
            "Success",
            Toast.LENGTH_SHORT
        ).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean = shopItemContainer == null

    private fun launchFragment(fragment: Fragment) {
        with(supportFragmentManager) {
            popBackStack()
            beginTransaction().replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setRecyclerView() {

        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()

        shopItemContainer = findViewById(R.id.fragment_container_view)
        Log.d("MainActivity", "shopItemContainer: $shopItemContainer")

        setOnShopItemClickListener()

        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(SHOP_LIST_ENABLED, MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(SHOP_LIST_DISABLED, MAX_POOL_SIZE)
        }

        setOnShopItemLongClickListener()
        setSwipeDeleteItem(rvShopList)
    }

    private fun setSwipeDeleteItem(rvShopList: RecyclerView) {
        val callback =
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val itemShop = shopListAdapter.currentList[position]

                    thread {
                        contentResolver.delete(
                            Uri.parse("content://com.example.shoplist/shopItems"),
                            null,
                            arrayOf(itemShop.id.toString())
                        )
                    }

//                    viewModel.removeShopItem(itemShop)
//                    Log.d("MainActivity", "remove: ${viewModel.mainViewModelState.value}")
                }
            }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setOnShopItemClickListener() {
        shopListAdapter.onShopClickListener = { shopItem ->
            Log.d("MainActivity", "Clicked on: ${shopItem.name}")
            Log.d("MainActivity", "Clicked on ID: ${shopItem.id}")

            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentEditItem(this, shopItem.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEditShopItemFragment(shopItem.id))
            }
        }
    }

    private fun setOnShopItemLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = { shopItem ->
            viewModel.changeEnableState(shopItem)
        }
    }
}