package com.example.firsttask
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firsttask.databinding.LayoutGridItemBinding


class GridAdapter(private val detailList: MutableList<Detail>, private val onClick: (Detail) -> Unit) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutGridItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detail = detailList[position]

        Glide.with(holder.binding.imgHost.context)
                .load(detail.photo_name[0])
                .into(holder.binding.imgHost)

        holder.binding.txtTitle.text = detail.category_name + " \u2022 " + detail.city_name
        holder.binding.txtSubTitle.text = detail.name
        holder.binding.txtPrice.text = "₹ " + detail.price.toString() + "/person"

        holder.binding.root.setOnClickListener {
            onClick(detail)
        }
    }

    override fun getItemCount(): Int {
        return detailList.size
    }
}