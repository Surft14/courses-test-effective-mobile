package com.example.appcourses.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.dto.model.Course
import com.example.data.repository.interfaces.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

class CourseViewModel(private val courseRepository: CourseRepository) : ViewModel() {

    private val _isAsc = MutableStateFlow(true)// <- Хранит то как отсартирован список
    val isAsc : MutableStateFlow<Boolean> = _isAsc

    private val _listCourses = MutableStateFlow(listOf<Course>())// <- Храниться список
    val listCourses: StateFlow<List<Course>> = _listCourses

    private val _favoriteCourses = MutableStateFlow<List<Course>>(emptyList())
    val favoriteCourses: StateFlow<List<Course>> = _favoriteCourses

    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse : StateFlow<Course?> = _selectedCourse

    private val _searchQuery = MutableStateFlow<String?>(null)

    init {
        observeAllCourses()
        observeFavorites()
    }

    private fun observeAllCourses() {
        viewModelScope.launch {
            courseRepository.getAllCourses()
                .collect { list ->
                    if (list.isEmpty()){
                        fetchCoursesFromNetworkIfNeeded()
                    } else{
                        _listCourses.value = list
                    }
                }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            courseRepository.getFavoriteCourses().collect { list ->
                if (list.isEmpty()){
                    fetchCoursesFromNetworkIfNeeded()
                } else{
                    _listCourses.value = list
                }
            }
        }
    }

    fun fetchCoursesFromNetworkIfNeeded() {
        viewModelScope.launch {
            try {
                val remoteCourses = courseRepository.getCourses()
                // Сохраняем в Room
                remoteCourses.first().forEach { courseRepository.insertCourse(it) }
                _listCourses.value = remoteCourses.first()
                // Room сам обновит StateFlow через observeAllCourses()
            } catch (e: Exception) {
                Log.e("LogViewModel", "Error fetchCoursesFromNetworkIfNeeded: ${e.message}")
            }
        }
    }

    fun changeHasLike(course: Course?) {
        viewModelScope.launch {
            val updated = course!!.copy(hasLike = !course.hasLike)
            courseRepository.updateCourse(updated)
            // обновить StateFlow, если нужно
        }
    }

    fun changeIsAsc(){
        _isAsc.value = !isAsc.value
    }

    fun selectCurse(course: Course){
        _selectedCourse.value = course
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
            _searchQuery.value = search
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