package com.eziosoft.moviesInParis.presentation.ui.mapScreen

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.moviesInParis.domain.Movie
import com.eziosoft.moviesInParis.domain.repository.LocalDatabaseRepository
import com.eziosoft.moviesInParis.navigation.Action
import com.eziosoft.moviesInParis.navigation.ActionDispatcher
import com.eziosoft.moviesInParis.presentation.ProjectDispatchers
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.heatmaps.HeatmapTileProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ScreenState(
    val markers: List<Movie> = emptyList(),
    val heatmapTileProvider: HeatmapTileProvider? = null,
    val selectedMovie: Movie? = null
)

private val DEFAULT_BOUNDS = LatLngBounds(
    LatLng(48.704137980738714, 2.1965618804097176),
    LatLng(49.01802783318708, 2.4790672212839127)
)

class MapScreenViewModel(
    private val dbRepository: LocalDatabaseRepository,
    val actionDispatcher: ActionDispatcher,
    private val projectDispatchers: ProjectDispatchers
) : ViewModel() {

    var screenState by mutableStateOf(ScreenState())
        private set

    private var searchString: String = ""
    private var mapBounds: LatLngBounds = DEFAULT_BOUNDS

    init {
        generateHeatMap()
        getMarkers(
            DEFAULT_BOUNDS
        )
        observeActions()
    }

    @OptIn(FlowPreview::class)
    private fun observeActions() {
        viewModelScope.launch {
            actionDispatcher.actionFlow.debounce(1000).collect() { action ->
                if (action is Action.SearchMovie) {
                    searchString = action.searchText
                    getMarkers(mapBounds)
                }
            }
        }
    }

    private fun generateHeatMap() {
        viewModelScope.launch(projectDispatchers.ioDispatcher) {
            val allMovies = dbRepository.getAll()
            val heatMapProvider = HeatmapTileProvider.Builder()
                .data(allMovies.map { LatLng(it.lat, it.lon) })
                .build()
            screenState = screenState.copy(heatmapTileProvider = heatMapProvider)
        }
    }

    fun getMarkers(bonds: LatLngBounds) {
        mapBounds = bonds

        Log.d("aaa", "getMarkers: $bonds")
        viewModelScope.launch(projectDispatchers.ioDispatcher) {
            val markers = dbRepository.getByLocation(
                bonds.southwest.latitude,
                bonds.southwest.longitude,
                bonds.northeast.latitude,
                bonds.northeast.longitude,
                numberOfResults = 100,
                searchString = searchString
            )
            withContext(projectDispatchers.mainDispatcher) {
                screenState = screenState.copy(markers = markers)
            }
        }
    }

    fun showMovieDetails(id: String, content: @Composable () -> Unit) {
        viewModelScope.launch {
            actionDispatcher.sharedParameters.selectedMovieId.value = id
            actionDispatcher.sharedParameters.bottomSheetContent.value = content
            actionDispatcher.dispatchAction(Action.ToggleBottomSheet(true))
        }
    }
}
