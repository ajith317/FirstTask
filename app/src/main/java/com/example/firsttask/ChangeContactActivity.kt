package com.example.firsttask

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.firsttask.Contact
import com.example.firsttask.ContactViewModel
import com.example.firsttask.R

class ChangeContactActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editNumber: EditText

    private lateinit var wordViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_contact)
        editName = findViewById(R.id.edit_name)
        editNumber = findViewById(R.id.edit_number)

        Toast.makeText(this,"click name or number to EDIT",Toast.LENGTH_SHORT).show()
        wordViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        val bundle = intent.getBundleExtra("bundle")
        var person  = bundle.getParcelable("contact") as Contact?

        editName.setText(person?.name)
        editNumber.setText(person?.phoneNumber)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            wordViewModel.change(contact = Contact(
                    person?.id,
                    editName.text.toString(),
                    editNumber.text.toString()))

            val intent = Intent(this@ChangeContactActivity, DayTwentyTask::class.java)
            startActivity(intent)
            finish()
        }

//        val imageView= findViewById(R.id.save_btn) as ImageView
//
//        imageView.setOnClickListener({
//            wordViewModel.change(contact = Contact(
//                    person?.id,
//                    editName.text.toString(),
//                    editNumber.text.toString()))
//
//            val intent = Intent(this@ChangeContactActivity, DayTwentyTask::class.java)
//            startActivity(intent)
//            finish()
//        })


    }
}
