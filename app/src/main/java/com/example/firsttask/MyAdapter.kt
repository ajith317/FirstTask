package com.example.firsttask

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.firsttask.DayElevenTask.Companion.isMultiSelectOn
import java.util.ArrayList

 class MyAdapter(val context: Context, val mainInterface: MainInterface) : RecyclerView.Adapter<MyViewHolder>(), ViewHolderClickListener {

    override fun onLongTap(index: Int) {
        if (!DayElevenTask.isMultiSelectOn) {
            DayElevenTask.isMultiSelectOn = true
        }
        addIDIntoSelectedIds(index)
    }
    override fun onTap(index: Int) {
        if (DayElevenTask.isMultiSelectOn) {
            addIDIntoSelectedIds(index)
        } else {
            Toast.makeText(context, "Position ${index + 1}", Toast.LENGTH_SHORT).show()
        }
    }

    fun addIDIntoSelectedIds(index: Int) {
        val id = modelList[index].id
        if (selectedIds.contains(id))
            selectedIds.remove(id)
        else
            selectedIds.add(id)

        notifyItemChanged(index)
        if (selectedIds.size < 1) DayElevenTask.isMultiSelectOn = false
        mainInterface.mainInterface(selectedIds.size)
    }
    var modelList: MutableList<MyModel> = ArrayList<MyModel>()
    val selectedIds: MutableList<String> = ArrayList<String>()

    override fun getItemCount() = modelList.size

     override fun onBindViewHolder(holder: MyViewHolder, index: Int) {
         holder?.devName?.setText(modelList[index].name)
         holder?.devPos?.setText(modelList[index].pos)



         val id = modelList[index].id

         if (selectedIds.contains(id)) {

             holder.checkImg.visibility=View.VISIBLE
             holder?.cardView?.foreground = ColorDrawable(ContextCompat.getColor(context,R.color.deletedLight))
         } else {

             holder.checkImg.visibility=View.GONE
             holder?.cardView?.foreground = ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent))
         }
     }



     fun deleteSelectedIDs() {
        if (selectedIds.size < 1) return
        val selectedIdIteration = selectedIds.listIterator();
        while (selectedIdIteration.hasNext()) {
            val selectedItemID = selectedIdIteration.next()
            Log.d(DayElevenTask.TAG, "The ID is $selectedItemID")
            var indexOfModelList = 0
            val modelListIteration: MutableListIterator<MyModel> = modelList.listIterator();
            while (modelListIteration.hasNext()) {
                val model = modelListIteration.next()
                if (selectedItemID.equals(model.id)) {
                    modelListIteration.remove()
                    selectedIdIteration.remove()
                    notifyItemRemoved(indexOfModelList)
                }
                indexOfModelList++
            }
           DayElevenTask.isMultiSelectOn = false
        }
    }




     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         val inflater = LayoutInflater.from(parent?.context)
         val itemView = inflater.inflate(R.layout.view_holder_layout, parent, false)
         return MyViewHolder(itemView, this)


     }


 }