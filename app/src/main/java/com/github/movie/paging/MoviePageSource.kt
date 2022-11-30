package com.github.movie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.movie.data.database.MovieDatabase
import com.github.movie.domain.models.Movie
import com.github.movie.domain.models.MovieData
import com.github.movie.domain.models.MovieType
import com.github.movie.data.networking.OmdbApi
import com.github.movie.utils.movieConvertDataToEntity
import com.github.movie.utils.movieConverter
import com.squareup.moshi.JsonDataException
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.lang.Exception


class MoviePageSource @AssistedInject constructor(
    private val db: MovieDatabase,
    private val api: OmdbApi,
    @Assisted("query") private val query: String,
    @Assisted("type") private val type: String
) : PagingSource<Int, MovieData>() {


    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        Timber.d("LoadStart")
        val pagePosition = params.key ?: 1
        return try {
            val queryResponse: Movie = if (query.isEmpty()) {
                api.getAllMovies(
                    title = "",
                    page = pagePosition,
                    type = type
                )
            } else {
                api.getAllMovies(
                    title = query,
                    page = pagePosition,
                    type = type
                )
            }
            db.movieDao().insertMoviesInDatabase(queryResponse.data.movieConvertDataToEntity())
            LoadResult.Page(
                data = queryResponse.data,
                prevKey = if (pagePosition == 1) null else pagePosition - 1,
                nextKey = if (queryResponse.data.isNullOrEmpty()) null else pagePosition + 1
            )
        } catch (t: JsonDataException) {
            emptyPage()
        } catch (exception: Exception) {
            if (query.isEmpty()) {
                emptyPage()
            } else {
                val response =
                    db.movieDao().getMovieByTitleAndType(query, MovieType.valueOf(type))
                        .movieConverter()
                LoadResult.Page(
                    data = response,
                    prevKey = null,
                    nextKey = null
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("query") query: String,
        @Assisted("type") type: String): MoviePageSource
    }
}

private fun emptyPage(): PagingSource.LoadResult<Int, MovieData> {
    return PagingSource.LoadResult.Page(
        data = emptyList(),
        prevKey = null,
        nextKey = null
    )

}

