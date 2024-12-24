package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList: MutableList<ShopItem> = mutableListOf()
    private val shopListLiveData = MutableLiveData<List<ShopItem>>()

    private var shopItemIdIncrement = 0

    init {
        for (i in 1..10)
            addShopItem(
                ShopItem(
                    name = "Name $i",
                    count = i,
                    enable = true,
                )
            )
    }

    override fun addShopItem(shopItem: ShopItem) {
        shopItemIdIncrement++
        shopItem.id = shopItemIdIncrement
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        deleteShopItem(oldShopItem)
        shopList.add(shopItem)
        updateList()
    }

    override fun getShopItemById(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw Exception("Shop item with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListLiveData

    private fun updateList() {
        shopListLiveData.value = shopList.toList()
    }

}