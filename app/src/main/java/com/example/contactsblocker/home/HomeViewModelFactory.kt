package com.example.contactsblocker.module.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsblocker.ContactsDao

class HomeViewModelFactory(private val contactsDao: ContactsDao) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(contactsDao) as T
    }
}