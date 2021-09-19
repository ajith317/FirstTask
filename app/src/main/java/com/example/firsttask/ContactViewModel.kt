package com.example.firsttask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ContactRepository
    val allContacts: LiveData<List<Contact>>

    init {
        val contactDAO = AppDatabase.getDatabase(application, scope).contactDAO()
        repository = ContactRepository(contactDAO)
        allContacts = repository.allContacts
    }

    fun insert(contact: Contact) = scope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }

    fun delete(id: Int) = scope.launch(Dispatchers.IO) {
        repository.delete(id)
    }

    fun change(contact: Contact) = scope.launch(Dispatchers.IO) {
        repository.change(contact)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}