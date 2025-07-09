package com.example.data.network

import com.example.data.dto.model.CourseDto
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.GET

//Интерфейс для работы с API-серверу
//В нем нужно описать что мы запрашиваем
interface CourseApi {
    //base url https://drive.usercontent.google.com/
    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourse(): ResponseBody
}