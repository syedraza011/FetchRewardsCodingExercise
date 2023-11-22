package com.example.rewardscodingexercise.network

import com.example.rewardscodingexercise.model.Items

import retrofit2.Call

import retrofit2.http.GET

interface RetrofitAPI {
    @GET("hiring.json")
    fun getAllItems(): Call<ArrayList<Items>>
}
