package com.example.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Станция для отправки HTTP запросов к Api-серверу
object InstanceApi {
    private val instRetrofit = Retrofit.Builder()
        .baseUrl("https://drive.usercontent.google.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val courseApi = instRetrofit.create(CourseApi::class.java)
}