package com.github.movie.utils

import android.app.Application
import com.github.movie.data.database.Database

class MovieApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}