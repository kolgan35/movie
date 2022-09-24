package com.github.movie.ui.downliaded_movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.movie.data.entity.MovieEntity
import com.github.movie.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.launch

class DownloadedMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = MovieRepositoryImpl()

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