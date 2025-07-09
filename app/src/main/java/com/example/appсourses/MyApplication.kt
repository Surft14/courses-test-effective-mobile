package com.example.appсourses

import android.app.Application
import com.example.data.database.db.provider.DatabaseProvider

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализируем базу данных здесь
        DatabaseProvider.initializeDatabase(this)
    }
}