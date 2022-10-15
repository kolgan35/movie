package com.github.movie.data.repository

import com.github.movie.data.database.Database
import com.github.movie.data.entity.MovieEntity
import com.github.movie.domain.models.MovieData
import com.github.movie.domain.repository.MovieRepository
import com.github.movie.utils.movieConverter
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl : MovieRepository {
    private val dao = Database.instance

    override suspend fun getAllMovies(): List<MovieData> {
        val response = dao.movieDao().getAllMoviesFromDatabase()
        return response.movieConverter()
    }


    override fun listenerMovie(): Flow<List<MovieEntity>>  {
        return dao.movieDao().observeMovies()
    }

}