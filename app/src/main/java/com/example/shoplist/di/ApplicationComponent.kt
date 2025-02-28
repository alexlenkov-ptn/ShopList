package com.example.shoplist.di

import android.app.Application
import android.content.ContentProvider
import com.example.shoplist.data.ShopListContentProvider
import com.example.shoplist.presentation.main.MainActivity
import com.example.shoplist.presentation.shopItem.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(shopItemFragment: ShopItemFragment)
    fun inject(provider: ShopListContentProvider)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}