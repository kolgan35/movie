package com.github.movie.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.movie.data.database.MovieDatabase
import com.github.movie.data.models.Movie
import com.github.movie.data.models.MovieData
import com.github.movie.data.models.MovieType
import com.github.movie.data.networking.OmdbApi
import com.github.movie.utils.movieConvertDataToEntity
import com.github.movie.utils.movieConverter
import com.squareup.moshi.JsonDataException
import timber.log.Timber
import java.lang.Exception

class MoviePageSource(
    private val db: MovieDatabase,
    private val api: OmdbApi,
    private val query: String,
    private val type: String
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
            LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        } catch (exception: Exception) {
            if (query.isEmpty()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
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
}