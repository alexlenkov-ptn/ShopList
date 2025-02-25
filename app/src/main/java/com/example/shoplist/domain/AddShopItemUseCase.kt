package com.example.shoplist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(private val shopItemRepository: ShopListRepository) {

    suspend fun addShopItem(shopItem: ShopItem) {
        shopItemRepository.addShopItem(shopItem)
    }

}