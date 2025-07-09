package com.example.appсourses

import android.app.Application
import com.example.data.database.db.databaseModule
import com.example.data.database.db.provider.DatabaseProvider
import com.example.data.network.networkModule
import com.example.data.repository.impl.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализируем базу данных здесь
        DatabaseProvider.initializeDatabase(this)

        //Запуск инекции зависимости что бы собрать объекты и их зависимости
        startKoin {
            androidContext(this@MyApplication)
            modules(
                networkModule,
                repositoryModule,
                databaseModule,
            )
        }
    }
}