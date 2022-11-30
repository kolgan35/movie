package com.github.movie.ui.downliaded_movies

import androidx.lifecycle.*
import com.github.movie.data.entity.MovieEntity
import com.github.movie.domain.repository.DownloadedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadedMoviesViewModel @Inject constructor(
    private val repo: DownloadedMovieRepository
) : ViewModel() {


    private val movieMutableLiveData = MutableLiveData<List<MovieEntity>>()

    val movieLiveData: LiveData<List<MovieEntity>>
        get() = movieMutableLiveData

    init {
        viewModelScope.launch {
            repo.listenerMovie().collect {
                movieMutableLiveData.postValue(it)
            }
        }
    }

}