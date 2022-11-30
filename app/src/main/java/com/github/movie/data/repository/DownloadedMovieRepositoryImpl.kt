package com.github.movie.data.repository

import com.github.movie.data.dao.MovieDao
import com.github.movie.data.entity.MovieEntity
import com.github.movie.domain.models.MovieData
import com.github.movie.domain.repository.DownloadedMovieRepository
import com.github.movie.utils.movieConverter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DownloadedMovieRepositoryImpl @Inject constructor(
    private val dao: MovieDao
) : DownloadedMovieRepository {

    override suspend fun getAllMovies(): List<MovieData> {
        val response = dao.getAllMoviesFromDatabase()
        return response.movieConverter()
    }


    override fun listenerMovie(): Flow<List<MovieEntity>>  {
        return dao.observeMovies()
    }

}