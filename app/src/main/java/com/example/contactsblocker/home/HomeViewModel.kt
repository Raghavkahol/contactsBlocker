package com.example.contactsblocker.module.home

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModel
import com.example.contactsblocker.CitiesDao
import com.example.contactsblocker.ViewModelLifecycleState
import com.example.contactsblocker.model.CityDetail


class HomeViewModel() : BaseViewModel() {


    var contacts = ObservableArrayList<CityDetail>()
    var isDataUnavalable = MutableLiveData<Boolean>().apply { false }


    fun getAllContacts(contentResolver : ContentResolver) {
        val phoneList = arrayListOf<String>()
        val nameList = arrayListOf<CityDetail>()
        val cr: ContentResolver = contentResolver
        val cur : Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,
        null, null, null, null);
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
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )
                    while (pCur?.moveToNext() == true) {
                        val phoneNo = pCur.getString(
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        phoneList.add(phoneNo)
                    }
                    val cityDetail : CityDetail = CityDetail()
                    cityDetail.name = name
                    cityDetail.number = phoneList
                    nameList.add(cityDetail)
                    pCur?.close()
                }

        }
            contacts.addAll(nameList)
        }
        cur?.close();
    }

    fun openCitySearchActivity() {
        lifecycleState.onNext(ViewModelLifecycleState.StartWithRequest(AppConstants.ONE_INT))
    }
}