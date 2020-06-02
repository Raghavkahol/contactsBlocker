package com.example.contactsblocker.module.home.cityDetail

import androidx.lifecycle.MutableLiveData
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModel
import com.example.contactsblocker.ContactsDao
import com.example.contactsblocker.ViewModelLifecycleState
import com.example.contactsblocker.model.Contact
import com.example.contactsblocker.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactDetailViewModel(private val contactsDao: ContactsDao) : BaseViewModel() {

    var name= MutableLiveData<String>().apply{AppConstants.EMPTY_STRING}
    var number= MutableLiveData<String>().apply{AppConstants.EMPTY_STRING}
    var isBlock = MutableLiveData<Boolean>().apply { false }
    private lateinit var contact : Contact

    fun fetchContactDetail(id: Int?) {
        bindDisposable {
            contactsDao.getContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    contact = it
                    updateView(it)
                }, {
                    it.printStackTrace()
                })
        }
    }

    private fun updateView(contact: Contact?) {
        contact?.let {
            name.value = it.name
            number.value = it.number
            isBlock.value = it.isBlock
        }
    }

    fun updateBlockStatus() {
        isBlock.value = isBlock.value != true
        contact.isBlock = isBlock.value
        updateDB()
    }

    private fun updateDB() {
        bindDisposable {
            contactsDao.insertContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                })
        }
    }


}