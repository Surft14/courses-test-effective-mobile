package com.example.data.database.db.di

import com.example.data.database.db.courses.dao.CourseDao
import com.example.data.database.db.provider.DatabaseProvider
import org.koin.dsl.module

val databaseModule = module {
    single<CourseDao> {
        DatabaseProvider.getDatabase().courseDao()
    }
}