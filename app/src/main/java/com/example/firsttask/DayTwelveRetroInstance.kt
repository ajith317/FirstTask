package com.example.firsttask

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DayTwelveRetroInstance {

    companion object{
        val base_url="https://gorest.co.in/public-api/"

        fun getRetroInstance():Retrofit
        {
            val logging=HttpLoggingInterceptor()
            logging.level=(HttpLoggingInterceptor.Level.BODY)
            val client=OkHttpClient.Builder()
            client.addInterceptor(logging)

            return  Retrofit.Builder().baseUrl(base_url).client(client.build())
                    .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}