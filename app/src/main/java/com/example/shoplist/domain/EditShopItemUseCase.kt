package com.example.shoplist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val shopItemRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopItemRepository.editShopItem(shopItem)
    }

}