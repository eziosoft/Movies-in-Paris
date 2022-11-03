package com.eziosoft.moviesInParis.data.remote.theMovieDbApi

import com.eziosoft.moviesInParis.data.remote.theMovieDbApi.models.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("search/movie")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<SearchResult>
}
