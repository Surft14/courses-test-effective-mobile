package com.example.data.database.db.provider

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.data.database.db.app.AppDatabase

//Через него получаем доступ к базе и инициализируем базу данных
object DatabaseProvider {
    private var instance: AppDatabase? = null

    fun initializeDatabase(context: Context){
        Log.d("MyLog", "DatabaseProvider: Starting database initialization")
        if (instance == null){
            Log.d("MyLog", "DatabaseProvider: Synchronization block entered")
            synchronized(AppDatabase::class){// Синхронизация для потокабезопасности
                Log.d("MyLog", "DatabaseProvider: start databaseBuilder")
                instance = Room.databaseBuilder(// Создание экземпляра базы данных
                    context.applicationContext,// Использует глобальный контекст приложения
                    AppDatabase::class.java, // Указывает класс базы данных т.е. какие у него таблицы
                    "courses_db",// Имя базы данных
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Log.d("MyLog", "DatabaseProvider: Database built successfully")
            }
        }
    }
    fun getDatabase(): AppDatabase {
        Log.d("MyLog", "DatabaseProvider: getDatabase")
        return instance ?: throw IllegalStateException("Database not initialized. Call initialize() first.")
    }
}