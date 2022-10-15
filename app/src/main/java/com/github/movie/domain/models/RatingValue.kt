package com.github.movie.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingValue(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
)
