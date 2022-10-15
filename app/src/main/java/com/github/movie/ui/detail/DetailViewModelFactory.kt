package com.github.movie.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.movie.data.repository.DetailMovieInfoRepositoryImpl

class DetailViewModelFactory: ViewModelProvider.Factory {

    private val repo = DetailMovieInfoRepositoryImpl()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repo = repo) as T
    }
}