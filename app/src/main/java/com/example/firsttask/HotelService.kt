package com.example.firsttask

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface HotelService {
    @GET("api/home")
    fun homeResponse(@Query("search_type") searchType: String, @Query("language") language: String, @Query("page") page: Int): Call<HomeResponse>


    @GET("api/explore")
    fun stayResponse(@Query("search_type") searchType: String, @Query("language") language: String, @Query("page") page: Int): Call<StayResponse>




    @Multipart
    @POST("api/edit_profile")
    fun updateProfile(@Part image: MultipartBody.Part,
                      @Part("gender") gender: RequestBody,
                      @Part("school") school: RequestBody,
                      @Part("phone") phone: RequestBody,
                      @Part("dob") dob: RequestBody,
                      @Part("work") work: RequestBody,
                      @Part("last_name") last_name: RequestBody,
                      @Part("user_location") user_location: RequestBody,
                      @Part("first_name") first_name: RequestBody,
                      @Part("about_me") about_me: RequestBody,
                      @Part("token") token: RequestBody,
                      @Part("email") email: RequestBody): Call<ResponseBody>
}
