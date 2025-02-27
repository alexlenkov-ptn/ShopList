package com.example.shoplist.presentation.shopItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem


const val SHOP_LIST_DISABLED = 0
const val SHOP_LIST_ENABLED = 1
const val MAX_POOL_SIZE = 25

class ShopListAdapter() : ListAdapter<ShopItem, ShopItemViewHolder>(
    ShopItemDiffCallback()
) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            SHOP_LIST_ENABLED -> R.layout.item_shop_enabled
            SHOP_LIST_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val viewHolder = ShopItemViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.tvName.text = "${shopItem.name}"
        holder.tvCount.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.itemView.setOnClickListener {
            onShopClickListener?.invoke(shopItem)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enable) SHOP_LIST_ENABLED
        else SHOP_LIST_DISABLED
    }
}