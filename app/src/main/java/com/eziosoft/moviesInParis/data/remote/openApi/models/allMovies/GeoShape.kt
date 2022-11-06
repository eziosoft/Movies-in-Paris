package com.eziosoft.moviesInParis.data.remote.openApi.models.allMovies

import androidx.annotation.Keep

@Keep
data class GeoShape(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)
