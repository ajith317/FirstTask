package com.example.firsttask

import android.app.AlertDialog
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class DayElevenTask : AppCompatActivity(),MainInterface{

    var myAdapter: MyAdapter? = null
    var actionMode: ActionMode? = null
    val list = ArrayList<MyModel>()
    lateinit var addBtn:Button

    companion object {
        var isMultiSelectOn = false
        val TAG = "DayElevenTask"
    }


    override fun mainInterface(size: Int) {
        if (actionMode == null) actionMode = startActionMode(ActionModeCallback())
        if (size > 0) actionMode?.setTitle("$size")

        else actionMode?.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_eleven_task)

        isMultiSelectOn = false
        val recyclerView = findViewById<RecyclerView>(R.id.day11_reyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(this, this)
        recyclerView.adapter = myAdapter

        addBtn=findViewById(R.id.day11_add_btn)

        addBtn.setOnClickListener({
                getData()
            myAdapter?.modelList = getData()
            myAdapter?.notifyDataSetChanged()
        })
    }

    inner class ActionModeCallback : ActionMode.Callback {
        var shouldResetRecyclerView = true

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.getItemId()) {
                R.id.action_delete -> {
                    shouldResetRecyclerView = false
                    myAdapter?.deleteSelectedIDs()
                    actionMode?.setTitle("") //remove item count from action mode.
                    actionMode?.finish()
                    return true
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.action_mode_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (shouldResetRecyclerView) {
                myAdapter?.selectedIds?.clear()
                myAdapter?.notifyDataSetChanged()
            }
            isMultiSelectOn = false
            actionMode = null
            shouldResetRecyclerView = true
        }
    }

    private fun getData(): MutableList<MyModel> {

        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_dev_details,null)
        val userName = v.findViewById<EditText>(R.id.day11_name_et)
        val userPos=v.findViewById<EditText>(R.id.day11_position_et)
        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("ADD") { addDialog, _ ->
            val devName = userName.text.toString()
            val devPos=userPos.text.toString()
            list.add(MyModel(getRandomID(), "$devName","$devPos"))
            addDialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()


        return list
    }

    fun getRandomID() = UUID.randomUUID().toString()

}