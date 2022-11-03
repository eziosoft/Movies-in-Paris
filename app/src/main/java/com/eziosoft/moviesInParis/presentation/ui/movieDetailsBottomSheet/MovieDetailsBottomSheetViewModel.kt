package com.eziosoft.moviesInParis.presentation.ui.movieDetailsBottomSheet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.moviesInParis.domain.Movie
import com.eziosoft.moviesInParis.domain.repository.LocalDatabaseRepository
import com.eziosoft.moviesInParis.navigation.ActionDispatcher
import com.eziosoft.moviesInParis.presentation.ProjectDispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ScreenState(val movie: Movie = Movie())

class MovieDetailsBottomSheetViewModel(
    private val projectDispatchers: ProjectDispatchers,
    private val dbRepository: LocalDatabaseRepository,
    val actionDispatcher: ActionDispatcher
) : ViewModel() {

    var state by mutableStateOf(ScreenState())
        private set

    fun getMovieById() {
        viewModelScope.launch(projectDispatchers.ioDispatcher) {
            val movie =
                dbRepository.getMovie(actionDispatcher.sharedParameters.selectedMovieId.value)

            movie?.let {
                withContext(projectDispatchers.mainDispatcher) {
                    state = state.copy(movie = it)
                }
            }
        }
    }
}
