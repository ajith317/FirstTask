package com.example.firsttask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DayTwelveViewModel :ViewModel(){

    lateinit var recyclerListData: MutableLiveData<DayTwelveUserList>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getUserListObserverable() : MutableLiveData<DayTwelveUserList> {
        return recyclerListData
    }

    fun getUsersList() {

        val retroInstance = DayTwelveRetroInstance.getRetroInstance().create(DayTwelveService::class.java)
        val call = retroInstance.getUsersList()
        call.enqueue(object : Callback<DayTwelveUserList>{
            override fun onFailure(call: Call<DayTwelveUserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<DayTwelveUserList>, response: Response<DayTwelveUserList>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }

    fun searchUser(searchText: String) {

        val retroInstance = DayTwelveRetroInstance.getRetroInstance().create(DayTwelveService::class.java)
        val call = retroInstance.searchUsers(searchText)
        call.enqueue(object : Callback<DayTwelveUserList>{
            override fun onFailure(call: Call<DayTwelveUserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<DayTwelveUserList>, response: Response<DayTwelveUserList>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }
}