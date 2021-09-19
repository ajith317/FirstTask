package com.example.firsttask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.firsttask.databinding.LayoutExploreItemBinding

class ExploreAdapter(private val exploreList: MutableList<Explore>, private val onClick: (Explore) -> Unit) : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutExploreItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutExploreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val explore = exploreList[position]

        Glide.with(holder.binding.imgExplore.context)
                .load(explore.image)
                .into(holder.binding.imgExplore)

        holder.binding.txtExploreTitle.text = explore.name

        holder.binding.root.setOnClickListener {
            onClick(explore)
        }
    }

    override fun getItemCount(): Int {
        return exploreList.size
    }
}
