package com.example.firsttask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_day_twelve_new_user.*
import kotlinx.android.synthetic.main.activity_day_twelve_task.*


class DayTwelveTask : AppCompatActivity(),DayTwelveRecylerAdapter.OnItemClickListener {

   lateinit var recyclerViewAdapter: DayTwelveRecylerAdapter
    lateinit var viewModel: DayTwelveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_twelve_task)

        intiRecyclerView()
        initViewModel()
        searchUser()


        day12_create_btn.setOnClickListener({
            startActivity(Intent(this@DayTwelveTask, DayTwelveNewUser::class.java))
        })

    }

    private fun searchUser() {
        day12_search_btn.setOnClickListener {
            if (!TextUtils.isEmpty(day12_search_et.text.toString())) {
                viewModel.searchUser(day12_search_et.text.toString())
            } else {
                viewModel.getUsersList()
            }
        }
    }


    private fun intiRecyclerView() {
        day12_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@DayTwelveTask)
            val decoration = DividerItemDecoration(this@DayTwelveTask, DividerItemDecoration.VERTICAL)

            addItemDecoration(decoration)
            recyclerViewAdapter = DayTwelveRecylerAdapter(this@DayTwelveTask)
            adapter = recyclerViewAdapter
        }
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DayTwelveViewModel::class.java)
        viewModel.getUserListObserverable().observe(this, Observer<DayTwelveUserList> {
            if (it == null) {
                Toast.makeText(this@DayTwelveTask, "Not found", Toast.LENGTH_SHORT).show()
            } else {
                recyclerViewAdapter.userList = it.data.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()

            }

        })
        viewModel.getUsersList()
    }

    override fun onItemEditCLick(user: DayTweleveUser) {
        val intent = Intent(this@DayTwelveTask, DayTwelveNewUser::class.java)
        intent.putExtra("user_id", user.id)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1000) {
            viewModel.getUsersList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}





