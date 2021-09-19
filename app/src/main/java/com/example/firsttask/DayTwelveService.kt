package com.example.firsttask

import androidx.room.Delete
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.http.*

interface DayTwelveService {



    //https://gorest.co.in/public-api/users
    @GET("users")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUsersList(): Call<DayTwelveUserList>

    //https://gorest.co.in/public-api/users?name=a
    @GET("users")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun searchUsers(@Query("name") searchText: String): Call<DayTwelveUserList>


    //https://gorest.co.in/public-api/users/121
    @GET("users/{user_id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUser(@Path("user_id") user_id: String): Call<DayTweleveUserResponse>


    @POST("users")
    @Headers("Accept:application/json", "Content-Type:application/json",
            "Authorization: Bearer 4aa38a91a2ef55b2293b19dd2c26478c3b02f64fe501f5239ce04e288829b04a")
    fun createUser(@Body params: DayTweleveUser): Call<DayTweleveUserResponse>

    @PATCH("users/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json",
            "Authorization: Bearer 4aa38a91a2ef55b2293b19dd2c26478c3b02f64fe501f5239ce04e288829b04a")
    fun updateUser(@Path("user_id") user_id: String, @Body params: DayTweleveUser): Call<DayTweleveUserResponse>

    @DELETE("users/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json",
            "Authorization: Bearer 4aa38a91a2ef55b2293b19dd2c26478c3b02f64fe501f5239ce04e288829b04a")
    fun deleteUser(@Path("user_id") user_id: String): Call<DayTweleveUserResponse>
}