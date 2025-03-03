package com.example.shoplist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.ShopListApp
import javax.inject.Inject

class ShopListContentProvider : ContentProvider() {

    private val component by lazy {
        (context as ShopListApp).component
    }

    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.shoplist", "shopItems", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.shoplist", "shopItems/*", GET_SHOP_ITEMS_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        Log.d("ShopListProvider", "query $uri code ${uriMatcher.match(uri)}")
        return when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                shopListDao.getShopItemListCursor()
            }

            else -> {
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null) {
            Log.d("ShopListContentProvider", "values is null | $values")
            return null
        }

        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enable")

                val shopItem = ShopItem(name, count, enabled, id)

                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))
            }
        }

        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object {
        const val GET_SHOP_ITEMS_QUERY = 100
    }
}