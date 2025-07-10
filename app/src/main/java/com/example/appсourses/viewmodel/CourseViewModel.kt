package com.example.appсourses.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.dto.model.Course
import com.example.data.repository.interfaces.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CourseViewModel(private val courseRepository: CourseRepository) : ViewModel() {

    private val _isAsc = MutableStateFlow(true)// <- Хранит то как отсартирован список
    val isAsc : MutableStateFlow<Boolean> = _isAsc

    private val _listCourses = MutableStateFlow(listOf<Course>())// <- Храниться список
    val listCourses: StateFlow<List<Course>> = _listCourses

    fun getCurses(){
        viewModelScope.launch {
            try {
                getCoursesFromDB()
            }catch (e : Exception){
                Log.e("LogViewModel", "Error: ${e.message}")
                getCursesFromAPI()
            }
        }
    }

    // Получения курсов из внешнего источника
    fun getCursesFromAPI() {
        viewModelScope.launch {
            try {
                val list = courseRepository.getCourses()
                _listCourses.value = list.first()
                for (course in _listCourses.value){
                    courseRepository.insertCourse(course)
                }
            }catch (e: Exception){
                Log.e("LogViewModel", "Error: ${e.message}")
            }
        }
    }

    // Получения курсов из кэша / локальной базы данных
    fun getCoursesFromDB(){
        viewModelScope.launch {
            try {
                val list = courseRepository.getAllCourses()
                _listCourses.value = list.first()
            }catch (e: Exception){
                Log.e("LogViewModel", "Error: ${e.message}")
            }
        }
    }

    // Получения курсов отмеченных избранными
    fun getFavoriteCourses(){
        viewModelScope.launch {
            try{
                val list = courseRepository.getFavoriteCourses()// <- берет из кэша / локальной базы данных
                _listCourses.value = list.first()
            }catch (e : Exception){
                Log.e("LogViewModel", "Error: ${e.message}")
            }
        }
    }

    // Сортирует по убыванию
    fun getCoursesFromDBOrderByDESC(){
        viewModelScope.launch {
            try{
                val list = courseRepository.getCoursesSortedByDateDesc()// <- берет из кэша / локальной базы данных
                _listCourses.value = list.first()
                _isAsc.value = false
            }catch (e : Exception){
                Log.e("LogViewModel", "Error: ${e.message}")
            }
        }
    }

    //Сортирует по возростанию
    fun getCoursesFromDBOrderByASC(){
        viewModelScope.launch {
            try{
                val list = courseRepository.getCoursesSortedByDateAsc()// <- берет из кэша / локальной базы данных
                _listCourses.value = list.first()
                _isAsc.value = true
            }catch (e : Exception){
                Log.e("LogViewModel", "Error: ${e.message}")
            }
        }
    }

    //Ищет по словам в title и в text
    fun getCoursesSearch(search: String){
        viewModelScope.launch {
            try{
                val list = courseRepository.getBySearchQuery(search)// <- берет из кэша / локальной базы данных
                _listCourses.value = list.first()
            }catch (e : Exception){
                Log.e("LogViewModel", "Error: ${e.message}")
            }
        }
    }

}