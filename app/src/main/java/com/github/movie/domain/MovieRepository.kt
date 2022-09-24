package com.github.movie.domain

import com.github.movie.data.entity.MovieEntity
import com.github.movie.data.models.MovieData
import kotlinx.coroutines.flow.Flow



interface MovieRepository {

    suspend fun getAllMovies(): List<MovieData>

    fun listenerMovie(): Flow<List<MovieEntity>>
}