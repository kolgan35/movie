package com.github.movie.ui.downliaded_movies

import android.util.Log
import androidx.lifecycle.*
import com.github.movie.data.entity.MovieEntity
import com.github.movie.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class DownloadedMoviesViewModel(
    private val repo: MovieRepository
) : ViewModel() {



    private val movieMutableLiveData = MutableLiveData<List<MovieEntity>>()

    val movieLiveData: LiveData<List<MovieEntity>>
        get() = movieMutableLiveData

    init {
        Log.d("AAA", "ViewModel created")
        viewModelScope.launch {
            repo.listenerMovie().collect {
                movieMutableLiveData.postValue(it)
            }
        }
    }

    override fun onCleared() {
        Log.d("AAA", "ViewModel cleared")
        super.onCleared()
    }
}