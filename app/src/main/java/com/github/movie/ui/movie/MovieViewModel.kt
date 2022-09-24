package com.github.movie.ui.movie

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.github.movie.data.database.Database
import com.github.movie.data.models.MovieData
import com.github.movie.data.networking.Networking
import com.github.movie.paging.MoviePageSource

class MovieViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val api = Networking.api
    private val db = Database.instance

    private val _movie = MutableLiveData<PagingData<MovieData>>()

    val movie: LiveData<PagingData<MovieData>>
    get() = _movie

    fun searchMovies(title: String, type: String) =
        Pager(
            PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10,
                prefetchDistance = 10
            ),
        ) {
            MoviePageSource(db, api, title, type)
        }.liveData
            .cachedIn(viewModelScope)
}