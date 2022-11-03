package com.eziosoft.moviesInParis.data.remote

import com.eziosoft.moviesInParis.data.remote.openApi.MoviesAPI
import com.eziosoft.moviesInParis.data.remote.openApi.OpenApiDataset
import com.eziosoft.moviesInParis.data.toMovie
import com.eziosoft.moviesInParis.domain.Movie
import com.eziosoft.moviesInParis.domain.repository.OpenApiRepository

class OpenApiRepositoryImpl(private val api: MoviesAPI) : OpenApiRepository {
    override suspend fun getAllMovies(): Result<List<Movie>?> {
        val response = api.getAllMovies(datasets = OpenApiDataset.MOVIES.title)
        return if (response.isSuccessful) {
            Result.success(response.body()?.map { it.toMovie() })
        } else {
            Result.failure(Exception(response.message()))
        }
    }
}
