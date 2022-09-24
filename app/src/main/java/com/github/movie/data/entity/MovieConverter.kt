package com.github.movie.data.entity

import androidx.room.TypeConverter
import com.github.movie.data.models.MovieData
import com.github.movie.data.models.MovieType

class MovieConverter {


    @TypeConverter
    fun convertMovieDataToMovieEntity(movieData: List<MovieData>): List<MovieEntity> {
        return movieData.map {
            MovieEntity(
                it.id,
                it.title,
                it.year,
                it.poster,
                MovieType.valueOf(it.type)
            )
        }
    }

    @TypeConverter
    fun convertMovieEntityToMovieData(movieEntity: List<MovieEntity>): List<MovieData> {
        return movieEntity.map {
            MovieData(
                it.movieId,
                it.title,
                it.year,
                it.poster,
                it.type.name
            )
        }
    }


}