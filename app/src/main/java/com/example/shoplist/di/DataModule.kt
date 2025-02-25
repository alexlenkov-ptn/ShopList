package com.example.shoplist.di

import android.app.Application
import com.example.shoplist.data.AppDataBase
import com.example.shoplist.data.ShopListDao
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(shopListRepositoryImpl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideShopListDao(application: Application) : ShopListDao =
            AppDataBase.getInstance(application).shopListDao()
    }

}