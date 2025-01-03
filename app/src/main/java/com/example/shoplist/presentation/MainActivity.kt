package com.example.shoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setRecyclerView()

        viewModel.mainViewModelState.observe(this) { state ->
            shopListAdapter.shopList = state
        }

    }

    private fun setRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()

        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(SHOP_LIST_ENABLED, MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(SHOP_LIST_DISABLED, MAX_POOL_SIZE)
        }

        setOnShopItemLongClickListener()
        setOnShopItemClickListener()
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
                    val itemShop = shopListAdapter.shopList[position]
                    viewModel.removeShopItem(itemShop)
                    Log.d("MainActivity", "remove: ${viewModel.mainViewModelState.value}")
                }
            }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setOnShopItemClickListener() {
        shopListAdapter.onShopClickListener = { shopItem ->
            Log.d("MainActivity", "Clicked on: ${shopItem.name}")
        }
    }

    private fun setOnShopItemLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = { shopItem ->
            viewModel.changeEnableState(shopItem)
        }
    }
}