package com.eziosoft.moviesInParis.data.local.room.movieDetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieDetails")
data class LocalMovieDetails(
    @PrimaryKey val title: String,
    val posterUrl: String,
    val description: String
)
