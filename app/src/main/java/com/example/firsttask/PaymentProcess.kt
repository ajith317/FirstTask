package com.example.firsttask

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_day_five_and_six.view.*
import org.json.JSONException
import org.json.JSONObject


class PaymentProcess : AppCompatActivity() {

    lateinit var txtId:TextView
    lateinit var txtAmnt:TextView
    lateinit var txtStatus:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_process)

        txtId=findViewById(R.id.txtId)
        txtAmnt=findViewById(R.id.txtAmnt)
        txtStatus=findViewById(R.id.txtStatus)

        val intent = intent
        try {
            val jsonDetails = JSONObject(intent.getStringExtra("PaymentDetails"))
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"))

        } catch (e: JSONException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDetails(jsonObject: JSONObject, stringExtra: String?) {


        txtId.setText(jsonObject.getString("id"))
        txtStatus.setText(jsonObject.getString("state"))
        txtAmnt.setText(jsonObject.getString(stringExtra))

    }
}