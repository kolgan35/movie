package com.github.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.movie.data.dao.MovieDao
import com.github.movie.data.entity.MovieEntity


@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

}