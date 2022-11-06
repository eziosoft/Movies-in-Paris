package com.eziosoft.moviesInParis.domain.repository

import com.eziosoft.moviesInParis.domain.Movie

interface OpenApiRepository {
    suspend fun getAllMovies(): Result<List<Movie>?>
}
