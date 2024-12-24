package com.example.shoplist.domain

class DeleteShopItemUseCase(private val shopItemRepository: ShopListRepository) {

    fun deleteShopItem(shopItem: ShopItem) {
        shopItemRepository.deleteShopItem(shopItem)
    }

}