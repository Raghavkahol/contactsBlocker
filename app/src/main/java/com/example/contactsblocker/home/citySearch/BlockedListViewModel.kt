package com.example.contactsblocker.module.home.citySearch

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.example.contactsblocker.BaseViewModel
import com.example.contactsblocker.ContactsDao
import com.example.contactsblocker.model.Contact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class BlockedListViewModel(private val contactsDao: ContactsDao) : BaseViewModel() {

    var contacts  = ObservableArrayList<Contact>()
    var isDataUnavalable = MutableLiveData<Boolean>().apply { false }

    var dataLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply{ false }

    fun fetchCityList() {
        bindDisposable {
            contactsDao.getcontactList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    with(contacts){
                        clear()
                        addAll(it)
                    }
                }, {
                    it.printStackTrace()
                })
        }
    }

    fun addToBlockList(number : String) {
        val contact = Contact()
        contact.name = number
        contact.number = number
        contact.isBlock = true
        bindDisposable {
            contactsDao.insertContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                   fetchCityList()
                }, {
                    it.printStackTrace()
                })
        }
    }
}