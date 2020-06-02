package com.example.contactsblocker.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ContactList(val contacts : ArrayList<CityDetail>)

data class CityDetail(
    val id: Int? = null,
    var name: String? = null,
    var number: ArrayList<String>? = null,
    var isBlock : Boolean? = null
)
