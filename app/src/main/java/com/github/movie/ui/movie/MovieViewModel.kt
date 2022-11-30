package com.github.movie.ui.movie

import androidx.lifecycle.*
import androidx.paging.*
import com.github.movie.data.database.MovieDatabase
import com.github.movie.domain.models.MovieData
import com.github.movie.data.networking.OmdbApi
import com.github.movie.paging.MoviePageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val pageSource: MoviePageSource.Factory
) : ViewModel() {

    fun searchMovies(title: String, type: String) = Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10,
                prefetchDistance = 10
            ),
        ) {
            pageSource.create(title, type)
        }.liveData
            .cachedIn(viewModelScope)


}