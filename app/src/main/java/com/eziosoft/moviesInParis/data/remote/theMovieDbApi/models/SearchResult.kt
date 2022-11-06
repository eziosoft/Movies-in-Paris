package com.eziosoft.moviesInParis.data.remote.theMovieDbApi.models

import androidx.annotation.Keep

@Keep
data class SearchResult(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
