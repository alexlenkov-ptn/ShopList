package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import java.util.Random
import java.util.TreeSet

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList: TreeSet<ShopItem> =
        sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()

    private var shopItemIdIncrement = 0

    init {
        for (i in 1..10000)
            addShopItem(
                ShopItem(
                    name = "Name $i",
                    count = i,
                    enable = kotlin.random.Random.nextBoolean(),
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
        updateList()
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