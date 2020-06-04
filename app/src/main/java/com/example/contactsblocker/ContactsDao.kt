package com.example.contactsblocker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contactsblocker.model.Contact
import com.example.contactsblocker.model.ContactList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(listTags: List<Contact?>?) : Completable

    @Query("SELECT * from Contact where is_block = 1")
    fun getBlockedContactListObservable() : Observable<List<Contact>>

    @Query("SELECT COUNT(*) from Contact")
    fun contactsCount() : Single<Int>

    @Query("SELECT * from Contact ORDER BY name")
    fun getcontactListFromDB(): Single<List<Contact>>

    @Query("SELECT * FROM Contact where id = :id")
    fun getContact(id : Int?): Single<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact): Completable

    @Query("SELECT COUNT(*) from Contact where number = :number")
    fun checkForBlockedContact(number : String) : Observable<Int>

}