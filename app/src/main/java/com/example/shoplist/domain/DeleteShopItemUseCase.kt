package com.example.shoplist.domain

class DeleteShopItemUseCase(private val shopItemRepository: ShopListRepository) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItemRepository.deleteShopItem(shopItem)
    }

}