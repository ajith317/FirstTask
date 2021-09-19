package com.example.firsttask

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ContactRepository(private val contactDao: ContactDAO) {

    val allContacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    @WorkerThread
    fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    @WorkerThread
    fun delete(id: Int) {
        contactDao.delete(id)
    }

    @WorkerThread
    fun change(contact: Contact) {
        contactDao.change(contact)
    }
}
