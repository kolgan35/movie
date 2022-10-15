package com.github.movie.ui.movie

import androidx.lifecycle.*
import androidx.paging.*
import com.github.movie.data.database.MovieDatabase
import com.github.movie.domain.models.MovieData
import com.github.movie.data.networking.OmdbApi
import com.github.movie.paging.MoviePageSource

class MovieViewModel(
    private val api: OmdbApi,
    private val db: MovieDatabase
) : ViewModel() {

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