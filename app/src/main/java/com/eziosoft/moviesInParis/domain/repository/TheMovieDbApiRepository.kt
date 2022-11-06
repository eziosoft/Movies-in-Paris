package com.eziosoft.moviesInParis.domain.repository

import com.eziosoft.moviesInParis.data.local.room.movieDetails.LocalMovieDetails

interface TheMovieDbApiRepository {

    suspend fun search(query: String): LocalMovieDetails?
}
