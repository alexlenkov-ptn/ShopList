package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList: MutableList<ShopItem> = mutableListOf()

    private var shopItemIdIncrement = 0

    override fun addShopItem(shopItem: ShopItem) {
        shopItemIdIncrement++
        shopItem.id = shopItemIdIncrement
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        deleteShopItem(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItemById(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw Exception("Shop item with id $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> = shopList.toList()

}