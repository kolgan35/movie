package com.github.movie.ui.downliaded_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.movie.data.repository.MovieRepositoryImpl

class DownloadedMovieViewModelFactory: ViewModelProvider.Factory {

    private val repo = MovieRepositoryImpl()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DownloadedMoviesViewModel(repo = repo) as T
    }
}