 package com.example.firsttask

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_day_twenty_one_task.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic"
class DayTwentyOneTask : AppCompatActivity() {

    val TAG = "DayTwentyOneTask"

    lateinit var ref:DatabaseReference
    lateinit var userList:MutableList<MyModel>
    lateinit var listView:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_twenty_one_task)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        listView=findViewById(R.id.firebase_listview)

        userList= mutableListOf()

        ref=FirebaseDatabase.getInstance().getReference("UserList")
        
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    userList.clear()
                    for (h in snapshot.children){
                        val user=h.getValue(MyModel::class.java)
                        userList.add(user!!)
                    }
                    val adapter=FirebaseRCAdapater(application,R.layout.home_page_desgin,userList)
                    listView.adapter=adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        val btnSend=findViewById<Button>(R.id.btnSend)
        val titleMsg=findViewById<EditText>(R.id.etTitle)
        val msg=findViewById<EditText>(R.id.etMessage)

        btnSend.setOnClickListener {
            val title = titleMsg.text.toString()
            val message = msg.text.toString()

            if(title.isNotEmpty() && message.isNotEmpty() ) {
                PushNotification(
                        NotificationData(title, message),
                        TOPIC
                ).also {
                    sendNotification(it)
                }
            }else
                Toast.makeText(this,"Field Required",Toast.LENGTH_SHORT).show()
        }


        dayTwentyOne_add_btn.setOnClickListener({
            getData()
        })


    }


    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }


    private fun getData() {

        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_dev_details,null)
         val userName = v.findViewById<EditText>(R.id.day11_name_et)
         val userPos=v.findViewById<EditText>(R.id.day11_position_et)
        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("ADD") { addDialog, _ ->
            val devName = userName.text.toString()
            val devPos=userPos.text.toString()
            //list.add(MyModel(getRandomID(), "$devName","$devPos"))

            if(devName.equals("") && devPos.equals(""))
                 Toast.makeText(this,"FieldEmpty",Toast.LENGTH_SHORT).show()
            else
                saveToFirebase(devName,devPos)

            addDialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()

    }

    private fun saveToFirebase(devName:String,devPos:String) {


        val userId=ref.push().key
        val userListItem=MyModel(userId.toString(),devName,devPos)

        ref.child(userId.toString()).setValue(userListItem).addOnCompleteListener{
            Toast.makeText(this,"Succuess full save",Toast.LENGTH_SHORT).show()
        }

    }




}