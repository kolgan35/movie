package com.github.movie.ui.detail

import MovieDetailInfo
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.movie.data.models.RatingValue
import com.github.movie.data.repository.DetailMovieInfoRepositoryImpl
import com.github.movie.utils.SingleLiveEvent

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DetailMovieInfoRepositoryImpl()
    private val toastSingleLiveEvent = SingleLiveEvent<String>()

    val toastLiveEvent: LiveData<String>
            get() = toastSingleLiveEvent

    suspend fun getInfo(id: String): MovieDetailInfo {
        return try {
            repo.getMovieInfo(id)
        } catch (t: Throwable) {
            toastSingleLiveEvent.postValue("Отсутствует интернет-соединение")
            emptyMovieDetailInfo()
        }
    }
}



private fun emptyMovieDetailInfo(): MovieDetailInfo {
    return MovieDetailInfo(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        listOf(
            RatingValue(
                "",
                "",
            )
        )
    )
}