package com.example.data.dto.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.database.db.courses.model.CourseEntity
import java.time.LocalDate
import java.time.ZoneId

// JSON-модель
data class CourseDto(
    val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rate: Double,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)

fun CourseDto.toEntity(): CourseEntity = CourseEntity(
    id = id ?: 0,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = LocalDate.parse(startDate).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000, // Конвертируем из String -> LocaleData -> Long
    hasLike = hasLike,
    publishDate = LocalDate.parse(publishDate).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000,
)
