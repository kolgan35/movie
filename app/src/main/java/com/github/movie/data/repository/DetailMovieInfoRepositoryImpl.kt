package com.github.movie.data.repository

import MovieDetailInfo
import android.util.Log
import com.github.movie.data.networking.Networking
import com.github.movie.domain.DetailMovieInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailMovieInfoRepositoryImpl : DetailMovieInfoRepository {
    private val api = Networking.api
    override suspend fun getMovieInfo(id: String): MovieDetailInfo {
        return withContext(Dispatchers.IO) {
                api.getDetailInfoByID(id)
        }
    }


}