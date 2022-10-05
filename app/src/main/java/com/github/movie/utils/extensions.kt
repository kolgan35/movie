package com.github.movie.utils


import MovieDetailInfo
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.movie.R
import com.github.movie.data.entity.MovieEntity
import com.github.movie.data.models.MovieData
import com.github.movie.data.models.MovieType
import com.github.movie.data.models.RatingValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}


fun <T : ViewBinding> ViewGroup.inflate(
    inflateBinding: (
        inflater: LayoutInflater,
        root: ViewGroup?,
        attachToRoot: Boolean
    ) -> T, attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)
    return inflateBinding(inflater, this, attachToRoot)
}

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        this@textChangedFlow.addTextChangedListener(textChangedListener)
        awaitClose {
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun RadioGroup.getMovieCheckedType(): Flow<String> {
    return callbackFlow {
        val checkedChangeListener = RadioGroup.OnCheckedChangeListener { _, i ->
            trySendBlocking(
                when (i) {
                    R.id.episodeButton -> MovieType.EPISODE.name
                    R.id.movieButton -> MovieType.MOVIE.name
                    R.id.seriesButton -> MovieType.SERIES.name
                    else -> {
                        MovieType.EPISODE.name
                    }
                }
            )
        }
        setOnCheckedChangeListener(checkedChangeListener)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }
}

fun List<MovieEntity>.movieConverter(): List<MovieData> {
    return this.map {
        MovieData(
            it.movieId,
            it.title,
            it.year,
            it.poster,
            it.type.name
        )
    }
}


fun List<MovieData>.movieConvertDataToEntity(): List<MovieEntity> {
    return this.map {
        MovieEntity(
            it.id,
            it.title,
            it.year,
            it.poster,
            MovieType.valueOf(it.type.uppercase(Locale.getDefault()))
        )
    }
}

fun MovieType.toString(movieType: MovieType): String = movieType.name

fun <T: Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}


