package com.github.movie.di

import com.github.movie.data.repository.DetailMovieInfoRepositoryImpl
import com.github.movie.data.repository.DownloadedMovieRepositoryImpl
import com.github.movie.domain.repository.DetailMovieInfoRepository
import com.github.movie.domain.repository.DownloadedMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideDownloadedMovieRepo(repo: DownloadedMovieRepositoryImpl): DownloadedMovieRepository {
        return repo
    }

    @Provides
    fun provideDetailMovieRepo(repo: DetailMovieInfoRepositoryImpl): DetailMovieInfoRepository {
        return repo
    }
}