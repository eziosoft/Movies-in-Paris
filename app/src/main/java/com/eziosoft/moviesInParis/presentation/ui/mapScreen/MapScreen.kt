package com.eziosoft.moviesInParis.presentation.ui.mapScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eziosoft.moviesInParis.domain.Movie
import com.eziosoft.moviesInParis.domain.repository.DBState
import com.eziosoft.moviesInParis.presentation.ui.components.Updating
import com.eziosoft.moviesInParis.presentation.ui.movieDetailsBottomSheet.MovieDetailsBottomSheet
import com.eziosoft.moviesInParis.presentation.ui.theme.PrimaryLight
import com.eziosoft.parisinnumbers.R
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider
import org.koin.androidx.compose.getViewModel

private val PARIS_POSITION = LatLng(48.8566, 2.3522)

@Composable
fun MapScreen(modifier: Modifier = Modifier.fillMaxSize()) {
    val viewModel: MapScreenViewModel = getViewModel()

    var mapType by remember {
        mutableStateOf(MapType.NORMAL)
    }

    Box(
        modifier = modifier
    ) {
        when (viewModel.screenState.dbState) {
            DBState.Unknown -> Unit
            DBState.Updating -> Updating()
            DBState.Ready -> {
                Map(
                    viewModel = viewModel,
                    mapType = mapType,
                    modifier = modifier,
                    onBoundsChange = { bonds ->
                        bonds?.let {
                            viewModel.getMarkers(it)
                        }
                    },
                    onMarkerClick = { markerId ->
                        viewModel.showMovieDetails(
                            id = markerId,
                            content = {
                                MovieDetailsBottomSheet()
                            }
                        )
                    }
                )

                var menuExpanded by remember {
                    mutableStateOf(false)
                }

                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(30.dp)
                ) {
                    Icon(
                        modifier = Modifier.clickable { menuExpanded = true },
                        imageVector = Icons.Filled.List,
                        contentDescription = "Layers"
                    )

                    DropdownMenu(
                        modifier = Modifier.background(PrimaryLight),

                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            mapType = MapType.NORMAL
                            menuExpanded = false
                        }) {
                            Text(stringResource(R.string.Normal))
                        }
                        DropdownMenuItem(onClick = {
                            mapType = MapType.SATELLITE
                            menuExpanded = false
                        }) {
                            Text(stringResource(R.string.Satellite))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Map(
    viewModel: MapScreenViewModel,
    mapType: MapType,
    onBoundsChange: (LatLngBounds?) -> Unit,
    modifier: Modifier = Modifier,
    onMarkerClick: (id: String) -> Unit
) {
    val markers: List<Movie> by remember(viewModel.screenState.markers) {
        mutableStateOf(viewModel.screenState.markers)
    }

    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(PARIS_POSITION, 11f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember(mapType) {
        mutableStateOf(
            MapProperties(
                mapType = mapType,
                mapStyleOptions = MapStyleOptions(mapStyle)
            )
        )
    }

    var heatmapTileProvider: HeatmapTileProvider? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = viewModel.screenState) {
        viewModel.screenState.let { screenState ->
            screenState.heatmapTileProvider?.let {
                heatmapTileProvider = it
            }
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            onBoundsChange(
                cameraPositionState.projection?.visibleRegion?.latLngBounds
            )
        }
    }

    Box(modifier) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            uiSettings = uiSettings,
            properties = properties,
            cameraPositionState = cameraPositionState
        ) {
            heatmapTileProvider?.let {
                TileOverlay(tileProvider = it)
            }

            markers.forEach { movie ->
                Marker(
                    state = MarkerState(position = LatLng(movie.lat, movie.lon)),
                    snippet = movie.title,
                    tag = movie.id,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                    onClick = {
                        onMarkerClick(it.tag as String)
                        true
                    }
                )
            }
        }
    }
}
