package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.collection.emptyLongSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

const val SHOP_LIST_DISABLED = 0
const val SHOP_LIST_ENABLED = 1
const val MAX_POOL_SIZE = 25

class ShopListAdapter() : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList: List<ShopItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


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
        val shopItem = shopList[position]
        holder.tvName.text = "${shopItem.name}"
        holder.tvCount.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enable) SHOP_LIST_ENABLED
        else SHOP_LIST_DISABLED
    }

    override fun getItemCount(): Int = shopList.size

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }
}