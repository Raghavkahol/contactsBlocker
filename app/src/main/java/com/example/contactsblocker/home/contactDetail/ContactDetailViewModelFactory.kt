package com.example.contactsblocker.module.home.cityDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsblocker.ContactsDao

class ContactDetailViewModelFactory(private val contactsDao: ContactsDao) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactDetailViewModel(contactsDao) as T
    }
}