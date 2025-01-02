package com.example.shoplist.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setRecyclerView()
        viewModel.mainViewModelState.observe(this) {
            adapter.shopList = it
        }
    }

    private fun setRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
        rvShopList.apply {
            recycledViewPool.setMaxRecycledViews(SHOP_LIST_ENABLED, MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(SHOP_LIST_DISABLED, MAX_POOL_SIZE)
        }
    }
}