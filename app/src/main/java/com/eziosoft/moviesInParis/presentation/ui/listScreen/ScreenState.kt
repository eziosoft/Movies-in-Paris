package com.eziosoft.moviesInParis.presentation.ui.listScreen

import com.eziosoft.moviesInParis.domain.Movie
import com.eziosoft.moviesInParis.domain.repository.DBState

data class ScreenState(
    val isLoading: Boolean = true,
    val items: List<Movie> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val dbState: DBState = DBState.Unknown
)
