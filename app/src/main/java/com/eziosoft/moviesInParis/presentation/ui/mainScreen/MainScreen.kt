package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.eziosoft.moviesInParis.navigation.Action
import com.eziosoft.moviesInParis.navigation.Destination
import com.eziosoft.moviesInParis.presentation.ui.listScreen.listScreen
import com.eziosoft.moviesInParis.presentation.ui.mapScreen.mapScreen
import com.eziosoft.moviesInParis.presentation.ui.theme.PrimaryLight
import com.eziosoft.parisinnumbers.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        var showMenu by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .padding(scaffoldPaddings1),
            topBar = {
                TopAppBar(
                    title = {
                        TopBar(
                            onSearch = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    viewModel.actionDispatcher.dispatchAction(Action.SearchMovie(it))
                                }
                            }
                        )
                    },
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.more))
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(PrimaryLight),
                                onClick = {
                                    showMenu = false
                                }
                            ) {
                                Text(stringResource(R.string.refresh_database))
                                Icon(Icons.Filled.Refresh, contentDescription = stringResource(R.string.refresh_database))
                            }
                        }
                    }
                )
            },
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
            NavHost(
                modifier = Modifier.padding(scaffoldPaddings2),
                navController = navController,
                startDestination = startDestination
            ) {
                listScreen()
                mapScreen()
            }
        }
    }
}
