package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.eziosoft.moviesInParis.navigation.Destination
import com.eziosoft.moviesInParis.presentation.ui.listScreen.listScreen
import com.eziosoft.moviesInParis.presentation.ui.mapScreen.mapScreen
import com.eziosoft.moviesInParis.presentation.ui.theme.PrimaryLight
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val viewModel = getViewModel<MainScreenViewModel>()

    val navController: NavHostController = rememberNavController()
    val startDestination: String = Destination.LIST_SCREEN.name

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.actionDispatcher.actionFlow.collect() { action ->
            processAction(
                action,
                navController,
                coroutineScope,
                bottomSheetScaffoldState
            )
        }
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                    .background(PrimaryLight)
                    .padding(8.dp)
            ) {
                viewModel.actionDispatcher.sharedParameters.bottomSheetContent.value()
            }
        },
        sheetPeekHeight = 0.dp
    ) { scaffoldPaddings1 ->
        Scaffold(
            modifier = Modifier.padding(scaffoldPaddings1),
            bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier,
                    navController = navController,
                    itemsList = listOf(
                        BottomNavItem(
                            name = stringResource(R.string.list),
                            route = Destination.LIST_SCREEN.name,
                            icon = Icons.Default.List
                        ),
                        BottomNavItem(
                            name = stringResource(R.string.map),
                            route = Destination.MAP_SCREEN.name,
                            icon = Icons.Default.LocationOn
                        )
                    ),
                    onItemClick = { bottomNavItem ->
                        navController.navigate(bottomNavItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { scaffoldPaddings2 ->
            ScreenContent(
                navController = navController,
                startDestination = startDestination,
                paddingValues = scaffoldPaddings2,
                onSearch = {
                    viewModel.search(text = it)
                }
            )
        }
    }
}

@Composable
private fun ScreenContent(
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
    onSearch: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onSearch = { onSearch(it) })
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = startDestination
        ) {
            listScreen()
            mapScreen()
        }
    }
}
