package com.example.contactsblocker

import androidx.room.TypeConverter
import com.example.contactsblocker.model.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListTypeConverter {
    @TypeConverter
    fun convertListToString(list: ArrayList<Contact?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertStringToList(arrayString: String?): ArrayList<Contact> {
        val gson = Gson()
        return gson.fromJson<ArrayList<Contact>>(
            arrayString,
            object : TypeToken<ArrayList<Contact?>?>() {}.type
        )
    }
}