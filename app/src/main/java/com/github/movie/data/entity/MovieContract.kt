package com.github.movie.data.entity

object MovieContract {

    const val TABLE_NAME = "movies"

    object Column {
        const val ID = "id"
        const val TITLE = "title"
        const val YEAR_OF_RELEASE = "year"
        const val POSTER = "poster"
        const val TYPE = "type"
    }
}