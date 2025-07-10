package com.example.appсourses.viewmodel.di

import com.example.appсourses.viewmodel.CourseViewModel
import com.example.appсourses.viewmodel.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CourseViewModel(get())
        MainScreenViewModel()
    } // get() берёт CourseRepository из другого модуля
}