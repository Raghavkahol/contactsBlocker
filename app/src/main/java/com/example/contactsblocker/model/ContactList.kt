package com.example.contactsblocker.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactList( @PrimaryKey(autoGenerate = true)
                        val id: Int? = null,
                        val contacts : ArrayList<Contact>)
@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String? = null,
    var number: String? = null,
    @ColumnInfo(name = "is_block")
    var isBlock : Boolean? = null
)
