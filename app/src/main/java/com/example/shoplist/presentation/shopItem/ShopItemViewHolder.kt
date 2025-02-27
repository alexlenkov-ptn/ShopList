package com.example.shoplist.presentation.shopItem

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName: TextView = view.findViewById<TextView>(R.id.tv_name)
    val tvCount: TextView = view.findViewById<TextView>(R.id.tv_count)
}