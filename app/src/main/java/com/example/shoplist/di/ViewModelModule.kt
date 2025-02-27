package com.example.shoplist.di

import androidx.lifecycle.ViewModel
import com.example.shoplist.presentation.main.MainViewModel
import com.example.shoplist.presentation.shopItem.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ViewModelComponent::class)
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(vm : MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(vm : ShopItemViewModel) : ViewModel

}