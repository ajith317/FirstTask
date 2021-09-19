package com.example.firsttask

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.firsttask.R

class NewContactActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)
        editName = findViewById(R.id.add_name)
        editNumber = findViewById(R.id.add_number)

       // val button = findViewById<Button>(R.id.button_save)
        val addImg=findViewById(R.id.save_btn) as ImageView
        addImg.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editName.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val contactName = editName.text.toString()
                val contactNumber = editNumber.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_NAME, contactName)
                replyIntent.putExtra(EXTRA_REPLY_NUMBER, contactNumber)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }


    }

    companion object {
        const val EXTRA_REPLY_NAME = "com.example.android.roomdatabasecrud.EXTRA_REPLY_NAME"
        const val EXTRA_REPLY_NUMBER = "com.example.android.roomdatabasecrud.EXTRA_REPLY_NUMBER"
    }
}
