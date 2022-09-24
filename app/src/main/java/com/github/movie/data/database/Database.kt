package com.github.movie.data.database

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: MovieDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}