package com.github.movie.di

import android.app.Application
import androidx.room.Room
import com.github.movie.data.dao.MovieDao
import com.github.movie.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDao(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }
}