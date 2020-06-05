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
    var name: String? = null,
    @PrimaryKey
    var number: String = "",
    @ColumnInfo(name = "is_block")
    var isBlock : Boolean? = null
)
