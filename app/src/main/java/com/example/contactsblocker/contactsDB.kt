package com.example.contactsblocker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.contactsblocker.model.Contact
import com.example.contactsblocker.model.ContactList


@TypeConverters(ListTypeConverter::class)
@Database(entities = [ContactList::class, Contact::class], version = 1)
abstract class contactsDB : RoomDatabase(){
    abstract val contactsDao : ContactsDao
}