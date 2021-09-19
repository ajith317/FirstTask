package com.example.firsttask

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface ContactDAO {

    //Alphabetic order
//    @Query("SELECT * from contacts ORDER BY name ASC")
//    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * from contacts ORDER BY id")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts")
    fun all(): List<Contact>

    @Insert
    fun insert(contact: Contact)

    @Insert(onConflict = REPLACE)
    fun change(contact: Contact)

    @Query("DELETE FROM contacts")
    fun deleteAll()

    @Query("DELETE FROM contacts WHERE id = :id")
    fun delete(id: Int)
}