package com.example.shoplist.domain

import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(private val shopItemRepository: ShopListRepository) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItemRepository.deleteShopItem(shopItem)
    }

}