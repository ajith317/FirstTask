package com.example.firsttask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import org.w3c.dom.Text

class FirebaseRCAdapater(val mCtx:Context,val layoutResId:Int, val userList:List<MyModel>)
    :ArrayAdapter<MyModel>(mCtx, layoutResId,userList)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater= LayoutInflater.from(mCtx)
        val view:View=layoutInflater.inflate(layoutResId,null)

        val userName= view.findViewById<TextView>(R.id.day21UserName)
        val userPos=view.findViewById<TextView>(R.id.day21_position_tv)
        val userDel=view.findViewById<ImageView>(R.id.day21_delete)

        val userList=userList[position]
        userName.text=userList.name.toString()
        userPos.text=userList.pos.toString()

        userDel.setOnClickListener({

        })
        return view
    }



}


