package com.example.data.repository.di

import com.example.data.repository.impl.CourseRepositoryImpl
import com.example.data.repository.interfaces.CourseRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CourseRepository> {
        CourseRepositoryImpl(
            api = get(),
            dao = get()
        )
    }
}