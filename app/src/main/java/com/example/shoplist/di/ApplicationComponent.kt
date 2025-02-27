package com.example.shoplist.di

import android.app.Application
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

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}