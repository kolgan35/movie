package com.github.movie.data.repository

import MovieDetailInfo
import com.github.movie.data.networking.OmdbApi
import com.github.movie.domain.repository.DetailMovieInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DetailMovieInfoRepositoryImpl @Inject constructor(
    private val api: OmdbApi
) : DetailMovieInfoRepository {

    override suspend fun getMovieInfo(id: String): MovieDetailInfo {
        return withContext(Dispatchers.IO) {
                api.getDetailInfoByID(id)
        }
    }


}