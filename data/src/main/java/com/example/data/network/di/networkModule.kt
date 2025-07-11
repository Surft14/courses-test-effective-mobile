package com.example.data.network.di

import com.example.data.network.CourseApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CourseApi> {
        get<Retrofit>().create(CourseApi::class.java)
    }
}
