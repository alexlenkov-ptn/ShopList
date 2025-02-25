package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enable = shopItem.enable,
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enable = shopItemDbModel.enable,
        id = shopItemDbModel.id,
    )

    fun mapListDbModelToListEntity(listDbModel: List<ShopItemDbModel>) =
        listDbModel.map { shopItemDbModel ->
            mapDbModelToEntity(shopItemDbModel)
        }

}