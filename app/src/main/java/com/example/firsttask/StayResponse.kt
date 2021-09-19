package com.example.firsttask

data class StayResponse(
        val data: List<Data>,
        val max_price: String,
        val min_price: String,
        val status_code: String,
        val success_message: String,
        val total_page: Int
)

data class Data(
        val bed_count: Int,
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
        val price: String,
        val rating: String,
        val reviews_count: Int,
        val room_type: String
)