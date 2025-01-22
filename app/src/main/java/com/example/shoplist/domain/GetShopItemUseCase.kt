package com.example.shoplist.domain

class GetShopItemUseCase(private val shopItemRepository: ShopListRepository) {

    suspend fun getShopItemById(shopItemId: Int) : ShopItem {
        return shopItemRepository.getShopItemById(shopItemId)
    }

}