package com.example.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.DeleteShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopListUseCase
import com.example.shoplist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    // TODO Так делать не надо. Поправим позже

    private val getShopListRepository = GetShopListUseCase(repository)
    private val deleteShopItem = DeleteShopItemUseCase(repository)
    private val editShopItem = EditShopItemUseCase(repository)

    private val _mainViewModelState = MutableLiveData<List<ShopItem>>()
    val mainViewModelState : LiveData<List<ShopItem>>
        get() = _mainViewModelState


    fun getShopList() {
        _mainViewModelState.value = getShopListRepository.getShopList()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItem.deleteShopItem(shopItem)
        getShopList()
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enable = !shopItem.enable)
        editShopItem.editShopItem(newItem)
        getShopList()
    }


}