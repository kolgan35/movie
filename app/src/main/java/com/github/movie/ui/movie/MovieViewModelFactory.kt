package com.github.movie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.movie.data.database.Database
import com.github.movie.data.networking.Networking

class MovieViewModelFactory : ViewModelProvider.Factory {

    private val api = Networking.api
    private val db = Database.instance

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(api = api, db = db) as T
    }
}