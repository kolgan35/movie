package com.github.movie.ui.detail

import MovieDetailInfo
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.github.movie.R
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
    fun loadPoster(frag: Fragment, uri: String, view: ImageView) {
        Glide.with(frag)
            .load(uri)
            .placeholder(R.drawable.ic_movie)
            .into(view)
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