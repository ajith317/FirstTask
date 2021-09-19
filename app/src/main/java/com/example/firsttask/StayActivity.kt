package com.example.firsttask

import android.R.attr.*
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firsttask.databinding.ActivityStayBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StayActivity : AppCompatActivity() {

    private var isLastPageReached: Boolean = false
    private var isLoadingList: Boolean = false

    lateinit var binding:ActivityStayBinding
    var page=1
    private var stayList: MutableList<Data?> = mutableListOf()

    private lateinit var stayAdapter: StayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityStayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide()

        initRecycler()
        response(page)
    }

    private fun initRecycler() {

        stayAdapter = StayAdapter(stayList) {
        }
        binding.stayRecyclerView.adapter = stayAdapter
        val linearLayoutManager = LinearLayoutManager(this@StayActivity, RecyclerView.VERTICAL, false)
        binding.stayRecyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {

            override fun loadMoreItems() {
                isLoadingList = true
                page += 1
                if (page>= 7) {
                    Toast.makeText(this@StayActivity, "Page is End", Toast.LENGTH_SHORT).show()
                    return
                }
                response(page)
            }

            override val isLastPage: Boolean
                get() = isLastPageReached
            override val isLoading: Boolean
                get() = isLoadingList
        })
        binding.stayRecyclerView.layoutManager = linearLayoutManager
    }

    private fun response(i: Int){
        val request = ServiceBuilder.buildService(HotelService::class.java)
        val call = request.stayResponse("all", "en", i)
        call.enqueue(object : Callback<StayResponse?> {
            override fun onResponse(call: Call<StayResponse?>, response: Response<StayResponse?>) {
                isLoadingList = false
                if (response.isSuccessful) {
                    val stayResponses = response.body()!!
                    stayList.addAll(stayResponses.data)
                    stayAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<StayResponse?>, t: Throwable) {
                isLoadingList = false
                Toast.makeText(this@StayActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

