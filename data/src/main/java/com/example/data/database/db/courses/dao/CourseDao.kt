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
interface CourseDao {

    //Для получени всех курсов из бд
    @Query("SELECT * FROM courses")
    suspend fun getAllCurses(): Flow<List<CourseEntity>>


    //Получение отсортированого списка курсов по возростанию (Выдаст сначала самые старые курсы, потом новые)
    @Query("SELECT * FROM courses ORDER BY publishDate ASC")
    suspend fun getCoursesSortedByDateAsc() : Flow<List<CourseEntity>>

    //Получение отсортированого списка курсов по убыванию (Сначала самые новые)
    @Query("SELECT * FROM courses ORDER BY publishDate DESC")
    suspend fun getCoursesSortedByDateDesc() : Flow<List<CourseEntity>>

    //Получить курсы в избранных
    @Query("SELECT * FROM courses WHERE hasLike LIKE 1")
    suspend fun getFavoriteCourses(): Flow<CourseEntity>

    //Вставить курс в бд если уже есть с таким id то заменит его
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    //Обновит курс
    @Update
    suspend fun updateCourse(course: CourseEntity)

    //Удалит курс
    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    //Удалит курсы по id
    @Query("DELETE FROM courses WHERE id IN (:courseIds)")
    suspend fun deleteCourses(courseIds: List<Int>)

    //Ищет курсы по тексту из поля запроса
    @Query("SELECT * FROM courses WHERE title LIKE '%' || :query || '%' OR text LIKE '%' || :query || '%'")
    suspend fun getBySearchQuery(query: String): Flow<List<CourseEntity>>

}