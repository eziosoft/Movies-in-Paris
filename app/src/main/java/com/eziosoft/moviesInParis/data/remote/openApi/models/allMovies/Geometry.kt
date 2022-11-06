package com.eziosoft.moviesInParis.data.remote.openApi.models.allMovies

import androidx.annotation.Keep

@Keep
data class Geometry(
    val coordinates: List<Double>,
    val type: String
)
