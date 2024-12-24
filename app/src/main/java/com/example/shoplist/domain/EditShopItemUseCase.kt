package com.example.shoplist.domain

class EditShopItemUseCase(private val shopItemRepository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem) {
        shopItemRepository.editShopItem(shopItem)
    }

}