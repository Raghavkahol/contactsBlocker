package com.example.contactsblocker.module.home.citySearch

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseRecyclerViewAdapter
import com.example.contactsblocker.R
import com.example.contactsblocker.model.CitySearchResult
import com.example.contactsblocker.module.home.cityDetail.CityDetailActivity
import com.example.contactsblocker.module.home.cityDetail.getCityDetailActivity
import kotlinx.android.synthetic.main.city_search_layout.view.*

class CitySearchAdapter(context: Context,  mList: List<CitySearchResult>) :  BaseRecyclerViewAdapter<CitySearchResult>(context, mList){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CitySearchViewHolder(LayoutInflater.from(context).inflate(R.layout.city_search_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CitySearchViewHolder)
            holder.bindView(mList?.get(position))

    }

    internal inner class CitySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bindView(city: CitySearchResult?) {
            val cityData = city?.matching_full_name?.split(AppConstants.COMMA)?.toTypedArray()
            itemView.apply {
                setOnClickListener(this@CitySearchViewHolder)
                cityData?.let {
                    city_name.text = it.get(AppConstants.ZERO_INT)
                    state_name.text = it.get(AppConstants.ONE_INT)
                    country_name.text = it.get(AppConstants.TWO_INT)
                }
            }
        }

        override fun onClick(v: View?) {
             val city: CitySearchResult? = mList?.get(adapterPosition)
             context.startActivity(getCityDetailActivity(context, city?._links?.city_item?.href))

        }
    }
}