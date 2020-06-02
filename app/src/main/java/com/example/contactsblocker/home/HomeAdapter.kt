package com.example.contactsblocker.module.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsblocker.BaseRecyclerViewAdapter
import com.example.contactsblocker.R
import com.example.contactsblocker.model.CityDetail
import kotlinx.android.synthetic.main.city_card_layout.view.*

class HomeAdapter( context: Context, mList: List<CityDetail>) : BaseRecyclerViewAdapter<CityDetail>(context, mList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeItemViewHolder(LayoutInflater.from(context).inflate(R.layout.city_card_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeItemViewHolder)
            holder.bindView(mList?.get(position))
    }

    internal inner class HomeItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(cityDetail: CityDetail?) {
            itemView.name.text = cityDetail?.name
        }
    }
}
