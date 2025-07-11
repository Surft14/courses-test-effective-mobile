package com.example.data.dto.model

import com.example.data.database.db.courses.model.CourseEntity
import java.time.LocalDate
import java.time.ZoneId

// JSON-модель
data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    var hasLike: Boolean,
    val publishDate: String
)

fun Course.toEntity(): CourseEntity = CourseEntity(
    id = id,
    title = title,
    text = text,
    price = price.replace(" ", "").toIntOrNull() ?: 0,
    rate = rate,
    startDate = LocalDate.parse(startDate)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli(),
    hasLike = hasLike,
    publishDate = LocalDate.parse(publishDate)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
)
