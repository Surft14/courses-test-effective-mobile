package com.example.data.repository.impl

import com.example.data.database.db.courses.dao.CourseDao
import com.example.data.network.CourseApi
import com.example.data.repository.interfaces.CourseRepository

class CourseRepositoryImpl(
    private val api: CourseApi,
    private val dao: CourseDao
) : CourseRepository {

}