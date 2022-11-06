package com.eziosoft.moviesInParis.data.remote.openApi.models.allMovies

data class GeoShape(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)
