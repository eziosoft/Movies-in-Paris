package com.eziosoft.moviesInParis.data.remote.openApi.models.allMovies

import androidx.annotation.Keep

@Keep
data class GeoPoint2d(
    val lat: Double,
    val lon: Double
)
