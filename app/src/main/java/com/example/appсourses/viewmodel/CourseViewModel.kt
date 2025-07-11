package com.example.appcourses.viewmodel

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

    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse : StateFlow<Course?> = _selectedCourse


    fun changeHasLike(){
        viewModelScope.launch {
            val course = _selectedCourse.value
            if (course != null) {
                val updated = course.copy(hasLike = !course.hasLike)
                _selectedCourse.value = updated
                courseRepository.updateCourse(updated)
            }
        }
    }

    fun changeHasLike(course: Course) {
        viewModelScope.launch {
            val updated = course.copy(hasLike = !course.hasLike)
            courseRepository.updateCourse(updated)
            // обновить StateFlow, если нужно
        }
    }

    fun changeIsAsc(){
        viewModelScope.launch {
            _isAsc.value = !isAsc.value
        }
    }

    fun selectCurse(course: Course){
        viewModelScope.launch {
            try {
                _selectedCourse.value = course
            }catch (e : Exception){
                Log.e("LogViewModel", "Error selectCurse: ${e.message}")
            }
        }
    }


    fun getCurses() {
        viewModelScope.launch {
            Log.i("LogViewModel", "START getCurses")
            try {
                //Получаем из БД
                var isDbEmpty = true
                getCoursesFromDB()
                //Если база пуста — грузим из API
                if (isDbEmpty) {
                    getCursesFromAPI()
                }
            } catch (e: Exception) {
                Log.e("LogViewModel", "Error getCurses: ${e.message}")
                getCursesFromAPI()
            }
        }
    }

    // Получения курсов из внешнего источника
    fun getCursesFromAPI() {
        viewModelScope.launch {
            Log.i("LogViewModel", "START getCursesFromAPI")
            try {
                val list =  courseRepository.getCourses()
                _listCourses.value = list.first()
                for (course in _listCourses.value){
                    courseRepository.insertCourse(course)
                }
            }catch (e: Exception){
                Log.e("LogViewModel", "Error getCursesFromAPI: ${e.message}")
            }
        }
    }

    // Получения курсов из кэша / локальной базы данных
    fun getCoursesFromDB(){
        viewModelScope.launch {
            Log.i("LogViewModel", "START getCoursesFromDB")
            try {
                courseRepository.getAllCourses()
                    .collect { list ->
                    _listCourses.value = list
                }
            }catch (e: Exception){
                Log.e("LogViewModel", "Error getCoursesFromDB: ${e.message}")
            }
        }
    }

    // Получения курсов отмеченных избранными
    fun getFavoriteCourses(){
        viewModelScope.launch {
            Log.i("LogViewModel", "START getFavoriteCourses")
            try{
                courseRepository.getFavoriteCourses()// <- берет из кэша / локальной базы данных
                    .collect { list ->
                        _listCourses.value = list
                    }
            }catch (e : Exception){
                Log.e("LogViewModel", "Error getFavoriteCourses: ${e.message}")
            }
        }
    }

    // Сортирует по убыванию
    fun getCoursesFromDBOrderByDESC(){
        viewModelScope.launch {
            Log.i("LogViewModel", "START getCoursesFromDBOrderByDESC")
            try{
                courseRepository.getCoursesSortedByDateDesc()// <- берет из кэша / локальной базы данных
                    .collect { list ->
                        _listCourses.value = list
                    }
            }catch (e : Exception){
                Log.e("LogViewModel", "Error getCoursesFromDBOrderByDESC: ${e.message}")
            }
        }
    }

    //Сортирует по возростанию
    fun getCoursesFromDBOrderByASC(){
        viewModelScope.launch {
            Log.i("LogViewModel", "START getCoursesFromDBOrderByASC")
            try{
                courseRepository.getCoursesSortedByDateAsc()// <- берет из кэша / локальной базы данных
                    .collect { list ->
                        _listCourses.value = list
                    }
            }catch (e : Exception){
                Log.e("LogViewModel", "Error getCoursesFromDBOrderByASC: ${e.message}")
            }
        }
    }

    //Ищет по словам в title и в text
    fun getCoursesSearch(search: String){
        viewModelScope.launch {
            Log.i("LogViewModel", "START getCoursesSearch")
            try{
               courseRepository.getBySearchQuery(search)// <- берет из кэша / локальной базы данных
                    .collect { list ->
                        _listCourses.value = list
                    }
            }catch (e : Exception){
                Log.e("LogViewModel", "Error getCoursesSearch: ${e.message}")
            }
        }
    }

}