package com.example.contactsblocker.module.home

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModel
import com.example.contactsblocker.ContactsDao
import com.example.contactsblocker.ViewModelLifecycleState
import com.example.contactsblocker.model.Contact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HomeViewModel(private val contactsDao: ContactsDao) : BaseViewModel() {


    var contacts = ObservableArrayList<Contact>()
    var isDataUnavalable = MutableLiveData<Boolean>().apply { false }


    fun getAllContacts(contentResolver : ContentResolver) {
        bindDisposable {
            contactsDao.contactsCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it==AppConstants.ZERO_INT) {
                       fetchContactsFromDevice(contentResolver)
                    } else {
                        getContactsFromDB()
                    }
                }, {
                    it.printStackTrace()
                })
        }

    }

    fun fetchContactsFromDevice(contentResolver: ContentResolver) {
        val nameList = arrayListOf<Contact>()
        val cr: ContentResolver = contentResolver
        val cur : Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,
            null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        if (cur?.getCount() != 0) {
            while (cur?.moveToNext() == true) {
                val id : String = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID));
                val name : String = cur.getString(cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                    ) > 0
                ) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null )
                    var phoneNo : String = ""
                    //Just take one number
                    while (pCur?.moveToNext() == true) {
                        phoneNo = pCur.getString(
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        break
                    }
                    val contact : Contact = Contact()
                    contact.name = name
                    contact.number = phoneNo
                    nameList.add(contact)
                    pCur?.close()
                }

            }
            contacts.addAll(nameList)
            insertInDB(nameList)
        }
        cur?.close();
    }

    private fun getContactsFromDB() {
        bindDisposable {
            contactsDao.getcontactListFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    with(contacts) {
                        clear()
                        addAll(it)
                    }
                }, {
                    it.printStackTrace()
                })
        }
    }

    private fun insertInDB(nameList: ArrayList<Contact>) {
        bindDisposable {
            contactsDao.insertContacts(nameList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    it.printStackTrace()
                })
        }
    }

    fun openCitySearchActivity() {
        lifecycleState.onNext(ViewModelLifecycleState.StartWithRequest(AppConstants.ONE_INT))
    }
}