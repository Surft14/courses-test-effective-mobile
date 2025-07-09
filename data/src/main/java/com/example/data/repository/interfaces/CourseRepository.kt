package com.example.data.repository.interfaces

import com.example.data.dto.model.CourseDto
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    fun getCourses(): Flow<List<CourseDto>>

    fun getAllCourses(): Flow<List<CourseDto>>

    fun getCoursesSortedByDateAsc(): Flow<List<CourseDto>>

    fun getCoursesSortedByDateDesc(): Flow<List<CourseDto>>

    fun getFavoriteCourses(): Flow<List<CourseDto>>

    suspend fun insertCourse(courseDto: CourseDto)

    suspend fun updateCourse(courseDto: CourseDto)

    suspend fun deleteCourse(courseDto: CourseDto)

    suspend fun deleteCourses(courseIds: List<Int>)

    fun getBySearchQuery(query: String) : Flow<List<CourseDto>>

}