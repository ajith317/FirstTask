package com.example.firsttask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firsttask.databinding.ActivityDayThirteenTaskBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DayThirteenTask : AppCompatActivity() {
    private var hostList : MutableList<Detail> = mutableListOf()
    private lateinit var hostAdapter: GridAdapter

    private var mostList : MutableList<Detail> = mutableListOf()
    private lateinit var mostAdapter: GridAdapter

    private var justList : MutableList<Detail> = mutableListOf()
    private lateinit var justAdapter: GridAdapter

    private var exploreList : MutableList<Explore> = mutableListOf()
    private lateinit var exploreAdapter: ExploreAdapter



    private lateinit var binding:ActivityDayThirteenTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayThirteenTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.hide()
        initRecycler()

        val request = ServiceBuilder.buildService(HotelService::class.java)
        val call = request.homeResponse("all", "en", 1)

        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful){
                    val homeResponse = response.body()!!
                    loadData(homeResponse)
                }
            }
            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Toast.makeText(this@DayThirteenTask, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initRecycler() {
        val recyclerView = binding.recycler
        exploreAdapter = ExploreAdapter(exploreList) {
            if (it.name == "Stays"){
                startActivity(Intent(this@DayThirteenTask, StayActivity::class.java))
            }
        }
        recyclerView.adapter = exploreAdapter
        recyclerView.setItemViewCacheSize(10)
        val linearLayoutManager = LinearLayoutManager(this@DayThirteenTask, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        exploreAdapter.notifyDataSetChanged()

        val hostRecycler = binding.hostRecycler
        hostAdapter = GridAdapter(hostList) {
        }
        hostRecycler.adapter = hostAdapter
        hostRecycler.setItemViewCacheSize(10)
        val gridLayoutManager = GridLayoutManager(this@DayThirteenTask, 2)
        hostRecycler.layoutManager = gridLayoutManager
        hostAdapter.notifyDataSetChanged()

        val mostRecycler = binding.mostViewedRecycler
        mostAdapter = GridAdapter(mostList) {
        }
        mostRecycler.adapter = mostAdapter
        mostRecycler.setItemViewCacheSize(10)
        val gridLayoutManager1 = GridLayoutManager(this@DayThirteenTask, 2)
        mostRecycler.layoutManager = gridLayoutManager1
        mostAdapter.notifyDataSetChanged()

        val justRecycler = binding.justRecycler
        justAdapter = GridAdapter(justList) {
        }
        justRecycler.adapter = justAdapter
        justRecycler.setItemViewCacheSize(10)
        val gridLayoutManager2 = GridLayoutManager(this@DayThirteenTask, 2)
        justRecycler.layoutManager = gridLayoutManager2
        justAdapter.notifyDataSetChanged()

    }

    private fun loadData(homeResponse: HomeResponse) {
        Glide.with(this@DayThirteenTask)
                .load(homeResponse.mobile_banner_image)
                .into(binding.imgHome)

        binding.txtTitle.text =homeResponse.mobile_banner_content


        exploreList.clear()
        exploreList.addAll(homeResponse.exploreList)
        exploreAdapter.notifyDataSetChanged()

        hostList.clear()
        hostList.addAll(homeResponse.Lists[0].Details)
        hostAdapter.notifyDataSetChanged()

        mostList.clear()
        mostList.addAll(homeResponse.Lists[1].Details)
        mostAdapter.notifyDataSetChanged()

        justList.clear()
        justList.addAll(homeResponse.Lists[2].Details)
        justAdapter.notifyDataSetChanged()

    }
}