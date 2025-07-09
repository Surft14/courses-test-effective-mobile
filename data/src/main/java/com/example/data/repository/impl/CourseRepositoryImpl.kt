package com.example.data.repository.impl

import com.example.data.database.db.courses.dao.CourseDao
import com.example.data.database.db.courses.model.toDto
import com.example.data.dto.model.CourseDto
import com.example.data.dto.model.toEntity
import com.example.data.network.CourseApi
import com.example.data.repository.interfaces.CourseRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CourseRepositoryImpl(
    private val api: CourseApi,
    private val dao: CourseDao,
) : CourseRepository {
    override fun getCourses(): Flow<List<CourseDto>> = flow {
        val gson = Gson()
        //ResponseBody превращяем в String
        val response = api.getCourse().string()
        // Конвертируем ResponseBody в JSON потом парсим его в CourseDto
        val listCourse: List<CourseDto> = gson.fromJson(
            response,
            object : TypeToken<List<CourseDto>>() {}.type
        )
        emit(listCourse)
    }

    override fun getAllCourses(): Flow<List<CourseDto>> {
        //Конвертируем Entity в Dto класс
        val listDto = dao.getAllCurses().map { entityList ->
            entityList.map { it.toDto() }
        }
        return listDto

    }

    override fun getCoursesSortedByDateAsc(): Flow<List<CourseDto>> {
        val listDto = dao.getCoursesSortedByDateAsc().map { entityList ->
            entityList.map { it.toDto() }
        }
        return listDto
    }

    override fun getCoursesSortedByDateDesc(): Flow<List<CourseDto>> {
        val listDto = dao.getCoursesSortedByDateDesc().map { entityList ->
            entityList.map { it.toDto() }
        }
        return listDto
    }

    override fun getFavoriteCourses(): Flow<List<CourseDto>> {
        val listDto = dao.getFavoriteCourses().map { entityList ->
            entityList.map { it.toDto() }
        }
        return listDto
    }

    override suspend fun insertCourse(courseDto: CourseDto) {
        val entity = courseDto.toEntity()// Ноборот конвертируем из Dto в Entity
        dao.insertCourse(entity)
    }

    override suspend fun updateCourse(courseDto: CourseDto) {
        val entity = courseDto.toEntity()
        dao.updateCourse(entity)
    }

    override suspend fun deleteCourse(courseDto: CourseDto) {
        val entity = courseDto.toEntity()
        dao.deleteCourse(entity)
    }

    override suspend fun deleteCourses(courseIds: List<Int>) {
        dao.deleteCourses(courseIds)
    }

    override fun getBySearchQuery(query: String): Flow<List<CourseDto>> {
        val listDto = dao.getBySearchQuery(query).map { entityList ->
            entityList.map { it.toDto() }
        }
        return listDto
    }

}