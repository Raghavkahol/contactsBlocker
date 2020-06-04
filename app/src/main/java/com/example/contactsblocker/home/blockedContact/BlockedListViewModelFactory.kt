package com.example.contactsblocker.module.home.citySearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsblocker.ContactsDao

class BlockedListViewModelFactory(private var contactsDao: ContactsDao) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BlockedListViewModel(contactsDao) as T
    }
}