package com.github.movie.domain.repository

import MovieDetailInfo


interface DetailMovieInfoRepository {
    suspend fun getMovieInfo(id: String): MovieDetailInfo
}