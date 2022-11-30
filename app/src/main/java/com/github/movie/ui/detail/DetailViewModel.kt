package com.github.movie.ui.detail

import MovieDetailInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.movie.domain.models.RatingValue
import com.github.movie.domain.repository.DetailMovieInfoRepository
import com.github.movie.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: DetailMovieInfoRepository
) : ViewModel() {

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