package com.example.data.database.db.courses.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.database.db.courses.model.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDAO {

    @Query("SELECT * FROM courses")
    suspend fun getAllCurses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses ORDER BY publishDate ASC")
    suspend fun etCoursesSortedByDateAsc() : Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses ORDER BY publishDate DESC")
    suspend fun etCoursesSortedByDateDesc() : Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE hasLike LIKE 1")
    suspend fun getFavoriteCourses(): Flow<CourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Query("DELETE FROM courses WHERE id IN (:courseIds)")
    suspend fun deleteCourses(courseIds: List<Int>)

    @Query("SELECT * FROM courses WHERE title LIKE '%' || :query || '%' OR text LIKE '%' || :query || '%'")
    suspend fun getBySearchQuery(query: String): Flow<List<CourseEntity>>

}