package com.example.firsttask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DayTwelveNewUserViewModel : ViewModel(){


    lateinit var createNewUserLiveData: MutableLiveData<DayTweleveUserResponse?>
    lateinit var loadUserData: MutableLiveData<DayTweleveUserResponse?>
    lateinit var deleteUserLiveData: MutableLiveData<DayTweleveUserResponse?>

    init {
        createNewUserLiveData = MutableLiveData()
        loadUserData = MutableLiveData()
        deleteUserLiveData = MutableLiveData()
    }

    fun getCreateNewUserObservable(): MutableLiveData<DayTweleveUserResponse?> {
        return  createNewUserLiveData
    }

    fun getDeleteUserObservable(): MutableLiveData<DayTweleveUserResponse?> {
        return  deleteUserLiveData
    }

    fun getLoadUserObservable(): MutableLiveData<DayTweleveUserResponse?> {
        return  loadUserData
    }

    fun createUser(user: DayTweleveUser) {
        val retroInstance = DayTwelveRetroInstance.getRetroInstance().create(DayTwelveService::class.java)
        val call = retroInstance.createUser(user)
        call.enqueue(object : Callback<DayTweleveUserResponse?> {
            override fun onFailure(call: Call<DayTweleveUserResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<DayTweleveUserResponse?>, response: Response<DayTweleveUserResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }

    fun updateUser(user_id: String, user: DayTweleveUser) {
        val retroInstance = DayTwelveRetroInstance.getRetroInstance().create(DayTwelveService::class.java)
        val  call= retroInstance.updateUser(user_id, user)
        call.enqueue(object : Callback<DayTweleveUserResponse?> {
            override fun onFailure(call: Call<DayTweleveUserResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<DayTweleveUserResponse?>, response: Response<DayTweleveUserResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }

    fun deleteUser(user_id: String?) {
        val retroInstance = DayTwelveRetroInstance.getRetroInstance().create(DayTwelveService::class.java)
        val call = retroInstance.deleteUser(user_id!!)
        call.enqueue(object : Callback<DayTweleveUserResponse?> {
            override fun onFailure(call: Call<DayTweleveUserResponse?>, t: Throwable) {
                deleteUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<DayTweleveUserResponse?>, response: Response<DayTweleveUserResponse?>) {
                if(response.isSuccessful) {
                    deleteUserLiveData.postValue(response.body())
                } else {
                    deleteUserLiveData.postValue(null)
                }
            }
        })
    }

    fun getUserData(user_id: String?) {
        val retroInstance = DayTwelveRetroInstance.getRetroInstance().create(DayTwelveService::class.java)
        val call = retroInstance.getUser(user_id!!)
        call.enqueue(object : Callback<DayTweleveUserResponse?> {
            override fun onFailure(call: Call<DayTweleveUserResponse?>, t: Throwable) {
                loadUserData.postValue(null)
            }

            override fun onResponse(call: Call<DayTweleveUserResponse?>, response: Response<DayTweleveUserResponse?>) {
                if(response.isSuccessful) {
                    loadUserData.postValue(response.body())
                } else {
                    loadUserData.postValue(null)
                }
            }
        })
    }


}