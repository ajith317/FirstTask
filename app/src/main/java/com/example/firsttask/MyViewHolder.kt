package com.example.firsttask

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View, val r_tap: ViewHolderClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnLongClickListener, View.OnClickListener {

    val devName: TextView
    val devPos: TextView
    val cardView: CardView
    val checkImg:ImageView


    init {
        devName = itemView.findViewById(R.id.myTextView)
        devPos=itemView.findViewById(R.id.day11_position_tv)
        cardView = itemView.findViewById(R.id.root_layout)
        cardView.setOnClickListener(this)
        cardView.setOnLongClickListener(this)
        checkImg=itemView.findViewById<ImageView>(R.id.day11_ivCheckBox)
    }

    override fun onClick(v: View?) {
        r_tap.onTap(adapterPosition)
    }

    override fun onLongClick(v: View?): Boolean {
        r_tap.onLongTap(adapterPosition)
        return true
    }
}