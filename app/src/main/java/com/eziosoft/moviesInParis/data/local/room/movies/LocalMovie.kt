package com.eziosoft.moviesInParis.data.local.room.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class LocalMovie(
    @PrimaryKey val id: String,
    val address: String,
    val year: String,
    val ardt_lieu: String,
    val lon: Double,
    val lat: Double,
    val startDate: String,
    val endDate: String,
    val placeId: String,
    val producer: String,
    val realisation: String,
    val title: String,
    val type: String

)
