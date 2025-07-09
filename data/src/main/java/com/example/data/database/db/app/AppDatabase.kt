package com.example.data.database.db.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.db.courses.model.CourseEntity
import com.example.data.database.db.courses.dao.CourseDAO

@Database(entities = [CourseEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun courseDao(): CourseDAO
}