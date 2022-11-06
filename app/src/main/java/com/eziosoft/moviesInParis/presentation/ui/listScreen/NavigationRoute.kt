package com.eziosoft.moviesInParis.presentation.ui.listScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.eziosoft.moviesInParis.navigation.Destination

fun NavGraphBuilder.listScreen() {
    composable(
        route = Destination.LIST_SCREEN.name
    ) {
        ListScreen()
    }
}
