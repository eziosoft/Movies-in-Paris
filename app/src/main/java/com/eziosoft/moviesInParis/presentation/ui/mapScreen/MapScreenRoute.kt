package com.eziosoft.moviesInParis.presentation.ui.mapScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.eziosoft.moviesInParis.navigation.Destination

fun NavGraphBuilder.mapScreen() {
    composable(
        route = Destination.MAP_SCREEN.name
    ) {
        MapScreen()
    }
}
