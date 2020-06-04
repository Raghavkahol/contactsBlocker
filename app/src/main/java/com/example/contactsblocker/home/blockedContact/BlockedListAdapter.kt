package com.example.contactsblocker.module.home.citySearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsblocker.BaseRecyclerViewAdapter
import com.example.contactsblocker.R
import com.example.contactsblocker.model.Contact
import com.example.contactsblocker.module.home.cityDetail.getContactDetailActivity
import kotlinx.android.synthetic.main.blocked_card_layout.view.*

class CitySearchAdapter(context: Context,  mList: List<Contact>) :  BaseRecyclerViewAdapter<Contact>(context, mList){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.blocked_card_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContactViewHolder)
            holder.bindView(mList?.get(position))

    }

    internal inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        fun bindView(contact: Contact?) {
            itemView.apply {
                setOnClickListener(this@ContactViewHolder)
                    name.text = contact?.name
                }
            }

        override fun onClick(view : View?) {
            val contact: Contact? = mList?.get(adapterPosition)
            context.startActivity(getContactDetailActivity(context, contact?.id))
        }
    }
    }
