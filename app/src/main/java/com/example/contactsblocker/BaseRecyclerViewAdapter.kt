package com.example.contactsblocker

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T>(context: Context, mList: List<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: List<T>? = null
    var context: Context
    var mInflater: LayoutInflater

    init {
        this.context = context
        this.mList = mList
        mInflater = LayoutInflater.from(context)
    }

    fun setData(list: List<T>?) {
        mList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }
}
