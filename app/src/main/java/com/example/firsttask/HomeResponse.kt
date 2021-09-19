package com.example.firsttask
import com.google.gson.annotations.SerializedName

data class HomeResponse(
        @SerializedName("Explore list")
        val exploreList: List<Explore>,
        val Lists: List<Lists>,
        val mobile_banner_content: String,
        val mobile_banner_image: String,
        val status_code: String,
        val success_message: String,
        val total_page: Int,
        val unread_count: Int
)

data class Explore(
        val image: String,
        val key: String,
        val name: String
)

data class Lists(
        val Details: List<Detail>,
        val Key: String,
        val Title: String
)

data class Detail(
        val bed_count: Int,
        val category: Int,
        val category_name: String,
        val city_name: String,
        val country_name: String,
        val currency_code: String,
        val currency_symbol: String,
        val id: Int,
        val instant_book: String,
        val is_covid_verified: Int,
        val is_wishlist: String,
        val latitude: String,
        val longitude: String,
        val name: String,
        val photo_name: List<String>,
        val price: Int,
        val rating: String,
        val reviews_count: Int,
        val type: String
)