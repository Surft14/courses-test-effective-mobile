package com.example.data.database.db.courses.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

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