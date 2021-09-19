package com.example.firsttask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.day_twelve_row_list.view.*

class DayTwelveRecylerAdapter(val clickListener:OnItemClickListener):RecyclerView.Adapter<DayTwelveRecylerAdapter.DayTwelveViewHolder>() {


    var userList = mutableListOf<DayTweleveUser>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayTwelveRecylerAdapter.DayTwelveViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.day_twelve_row_list, parent, false)
        return DayTwelveViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: DayTwelveRecylerAdapter.DayTwelveViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemEditCLick(userList[position])
        }
    }

    class DayTwelveViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewName = view.day12_name_tv
        val textViewEmail = view.day12_email_tv
        val textViewStats = view.day12_status_tv
        val textViewGender=view.day12_gender_tv

        fun bind(data : DayTweleveUser) {
            textViewName.text = data.name
            textViewEmail.text = data.email
            textViewStats.text = data.status
            textViewGender.text=data.gender
        }
    }

    interface OnItemClickListener {
        fun onItemEditCLick(user : DayTweleveUser)
    }


}