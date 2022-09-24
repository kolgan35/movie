package com.github.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.movie.data.entity.MovieContract
import com.github.movie.data.entity.MovieEntity
import com.github.movie.data.models.MovieType
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${MovieContract.TABLE_NAME}")
    suspend fun getAllMoviesFromDatabase(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesInDatabase(movies: List<MovieEntity>)

    @Query("SELECT * FROM ${MovieContract.TABLE_NAME} WHERE ${MovieContract.Column.TITLE} LIKE '%' || :title || '%' AND ${MovieContract.Column.TYPE} LIKE :type")
    suspend fun getMovieByTitleAndType(title: String, type: MovieType): List<MovieEntity>

    @Query("SELECT * FROM ${MovieContract.TABLE_NAME}")
    fun observeMovies(): Flow<List<MovieEntity>>
}