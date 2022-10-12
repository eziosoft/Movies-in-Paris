package com.eziosoft.parisinnumbers.domain.repository

import com.eziosoft.parisinnumbers.domain.Movie

interface DatabaseRepository {
    suspend fun fillDb()
    suspend fun getAll(): List<Movie>
    suspend fun getMovie(id: String): Movie?
    suspend fun getPaged(rowNumber: Int, pageSize: Int, searchString: String): List<Movie>
    suspend fun getByLocation(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        numberOfResults: Int
    ): List<Movie>
}