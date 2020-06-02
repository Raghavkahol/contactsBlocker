package com.example.contactsblocker

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("recyclerViewAdapter")
fun <T> setRecyclerViewAdapter(recyclerView: RecyclerView, itemList: List<T>?) {
    if (itemList == null) {
        return
    }
    val adapter = recyclerView.adapter as BaseRecyclerViewAdapter<T>
    adapter.setData(itemList)

}