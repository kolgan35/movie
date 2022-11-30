package com.github.movie.domain.repository

import com.github.movie.data.entity.MovieEntity
import com.github.movie.domain.models.MovieData
import kotlinx.coroutines.flow.Flow



interface DownloadedMovieRepository {

    suspend fun getAllMovies(): List<MovieData>
    fun listenerMovie(): Flow<List<MovieEntity>>

}