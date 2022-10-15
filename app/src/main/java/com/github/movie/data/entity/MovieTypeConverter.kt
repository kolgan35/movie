package com.github.movie.data.entity

import androidx.room.TypeConverter
import com.github.movie.domain.models.MovieType

class MovieTypeConverter {

    @TypeConverter
    fun convertTypeToEnum(type: String): MovieType = MovieType.valueOf(type)

    @TypeConverter
    fun convertEnumToType(type: MovieType): String = type.name
}