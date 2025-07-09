package com.example.data.database.db.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.db.courses.model.CourseEntity
import com.example.data.database.db.courses.dao.CourseDAO

//База данных объединяет сущности и DAO.
@Database(entities = [CourseEntity::class], version = 1) //Указывает, какие таблицы используются в базе данных
// и версия базы данных если измениться то версию нужно будет увеличить
abstract class AppDatabase: RoomDatabase() {
    abstract fun courseDao(): CourseDAO
}