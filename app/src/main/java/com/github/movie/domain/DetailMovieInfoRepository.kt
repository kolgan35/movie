package com.github.movie.domain

import MovieDetailInfo


interface DetailMovieInfoRepository {
    suspend fun getMovieInfo(id: String): MovieDetailInfo
}