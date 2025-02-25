package com.example.shoplist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val shopItemRepository: ShopListRepository) {

    suspend fun getShopItemById(shopItemId: Int): ShopItem {
        return shopItemRepository.getShopItemById(shopItemId)
    }

}