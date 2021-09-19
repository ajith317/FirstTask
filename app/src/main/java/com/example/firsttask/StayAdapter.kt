package com.example.firsttask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firsttask.databinding.LayoutStayItemBinding

class StayAdapter(private val stayList: MutableList<Data?>, private val onClick: (Data) -> Unit): RecyclerView.Adapter<StayAdapter.StayViewHolder>() {



    class StayViewHolder(val binding: LayoutStayItemBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StayViewHolder {
        return  StayViewHolder(
                LayoutStayItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: StayAdapter.StayViewHolder, position: Int) {
        val stay=stayList[position]
        Glide.with(holder.binding.imageSlider.context)
                .load(stay!!.photo_name[0])
                .into(holder.binding.imageSlider)

        holder.binding.ratings.text=stay?.rating
        holder.binding.sharedRoom.text=stay?.room_type + " \u2022 " +stay.city_name
        holder.binding.name.text=stay?.name
        holder.binding.roomPrice.text="₹ " + stay?.price.toString() + "/night"
    }

    override fun getItemCount(): Int {
        return stayList.size
    }

}