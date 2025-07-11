package com.example.data.database.db.courses.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.dto.model.Course
import java.io.Serializable
import java.time.Instant
import java.time.ZoneId

//Таблица с курсами в бд Room
@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)val id: Int? = null,
    val title: String,
    val text: String,
    val price: Int,
    val rate: Double,
    val startDate: Long,
    val hasLike: Boolean = false,
    val publishDate: Long
): Serializable

//Функция для конвертации
fun CourseEntity.toDto(): Course = Course(
    id = id ?: 0,
    title = title,
    text = text,
    price = price.toString(),
    rate = rate,
    startDate = Instant.ofEpochMilli(startDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .toString(),
    hasLike = hasLike,
    publishDate = Instant.ofEpochMilli(publishDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .toString()
)