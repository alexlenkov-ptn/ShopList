package com.example.shoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel.getShopList()

        Log.d("MainActivity", "VM State: ${viewModel.mainViewModelState.value}")

        viewModel.deleteShopItem(ShopItem(name="Name 1", count=1, enable=true, id=1))

        Log.d("MainActivity", "VM State: ${viewModel.mainViewModelState.value}")

        viewModel.changeEnableState(ShopItem(name="Name 2 changed", count=2, enable=true, id=2))

        Log.d("MainActivity", "VM State: ${viewModel.mainViewModelState.value}")

    }


}