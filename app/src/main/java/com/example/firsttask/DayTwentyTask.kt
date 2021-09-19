package com.example.firsttask

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_day_twenty_task.*

class DayTwentyTask : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private lateinit var wordViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_twenty_task)

        val adapter = ContactsListAdapter(this)



        adapter.deleteClickListener = object : ContactsListAdapter.DeleteClickListener {
            override fun onBtnClick(id: Int) {
                val builder = AlertDialog.Builder(this@DayTwentyTask)
                builder.setTitle("Are Sure to Delete")
                builder.setMessage("Delete Contact")
                builder.setPositiveButton("Yes"){
                    dialogInterface, which ->
                    wordViewModel.delete(id)
                    Toast.makeText(applicationContext,"Delete Succesfully",Toast.LENGTH_LONG).show()
                }
                builder.setNeutralButton("No"){dialogInterface , which ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

            }
        }

        adapter.changeClickListener = object : ContactsListAdapter.ChangeClickListener {
            override fun onBtnClick(contact: Contact) {
                val intent = Intent(this@DayTwentyTask, ChangeContactActivity::class.java)
                var bundle = Bundle()
                bundle.putParcelable("contact",contact)
                intent.putExtra("bundle",bundle)
                startActivity(intent)
            }

        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        wordViewModel.allContacts.observe(this, Observer { contacts ->
            contacts.let {
                adapter.setContacts(it!!)
            }
        })

        btAdd.setOnClickListener {
            val intent = Intent(this@DayTwentyTask, NewContactActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val contact = Contact(null, data.getStringExtra(NewContactActivity.EXTRA_REPLY_NAME), data.getStringExtra(NewContactActivity.EXTRA_REPLY_NUMBER))
                wordViewModel.insert(contact)
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    "Contact not saved because it is empty.",
                    Toast.LENGTH_LONG
            ).show()
        }
    }
}