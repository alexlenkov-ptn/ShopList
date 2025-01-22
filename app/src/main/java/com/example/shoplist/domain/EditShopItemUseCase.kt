package com.example.shoplist.domain

class EditShopItemUseCase(private val shopItemRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopItemRepository.editShopItem(shopItem)
    }

}