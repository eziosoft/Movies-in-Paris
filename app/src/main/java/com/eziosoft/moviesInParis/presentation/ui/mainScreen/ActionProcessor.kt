package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import com.eziosoft.moviesInParis.navigation.Action
import com.eziosoft.moviesInParis.navigation.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
fun processAction(
    action: Action,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    when (action) {
        is Action.Navigate ->
            when (action.destination) {
                Destination.LIST_SCREEN -> navController.popBackStack(
                    Destination.LIST_SCREEN.name,
                    inclusive = false
                )
                Destination.DETAILS_SCREEN -> navController.navigate(
                    Destination.DETAILS_SCREEN.name
                )
                Destination.MAP_SCREEN -> navController.popBackStack(
                    Destination.MAP_SCREEN.name,
                    inclusive = false
                )
            }
        is Action.ToggleBottomSheet -> {
            coroutineScope.launch {
                if (action.expanded) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }

        is Action.ShowSnackbar ->
            coroutineScope.launch {
                bottomSheetScaffoldState.snackbarHostState.showSnackbar(action.text)
            }
        else -> {}
    }
}
