package com.example.appcourses.viewmodel.di

import com.example.appcourses.viewmodel.MainScreenViewModel
import com.example.appcourses.viewmodel.CourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CourseViewModel(get()) }
    viewModel { MainScreenViewModel() } // get() берёт CourseRepository из другого модуля
}