package com.github.movie.data.entity

import androidx.room.*
import com.github.movie.data.models.MovieType

@Entity(tableName = MovieContract.TABLE_NAME)
@TypeConverters(
    MovieConverter::class,
    MovieTypeConverter::class
)

data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = MovieContract.Column.ID)
    val movieId: String,
    @ColumnInfo(name = MovieContract.Column.TITLE)
    val title: String,
    @ColumnInfo(name = MovieContract.Column.YEAR_OF_RELEASE)
    val year: String,
    @ColumnInfo(name = MovieContract.Column.POSTER)
    val poster: String,
    @ColumnInfo(name = MovieContract.Column.TYPE)
    val type: MovieType,
)
