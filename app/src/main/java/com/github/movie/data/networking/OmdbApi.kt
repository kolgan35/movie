package com.github.movie.data.networking

import MovieDetailInfo
import androidx.annotation.IntRange
import com.github.movie.data.const.ApiConst
import com.github.movie.domain.models.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET(ApiConst.API_KEY)
    suspend fun getAllMovies(
        @Query("s") title: String,
        @Query("type") type: String = "movie",
        @Query("page") @IntRange(from = 1) page: Int = 1
    ): Movie

    @GET(ApiConst.API_KEY)
    suspend fun getDetailInfoByID(
        @Query("i") id: String
    ): MovieDetailInfo
}