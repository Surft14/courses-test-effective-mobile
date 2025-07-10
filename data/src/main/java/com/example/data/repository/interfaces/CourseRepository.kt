package com.example.data.repository.interfaces

import com.example.data.dto.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    fun getCourses(): Flow<List<Course>>

    fun getAllCourses(): Flow<List<Course>>

    fun getCoursesSortedByDateAsc(): Flow<List<Course>>

    fun getCoursesSortedByDateDesc(): Flow<List<Course>>

    fun getFavoriteCourses(): Flow<List<Course>>

    suspend fun insertCourse(course: Course)

    suspend fun updateCourse(course: Course)

    suspend fun deleteCourse(course: Course)

    suspend fun deleteCourses(courseIds: List<Int>)

    fun getBySearchQuery(query: String) : Flow<List<Course>>

}