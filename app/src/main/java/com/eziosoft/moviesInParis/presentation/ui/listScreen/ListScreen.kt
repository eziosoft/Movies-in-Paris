package com.eziosoft.moviesInParis.presentation.ui.listScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eziosoft.moviesInParis.domain.repository.DBState
import com.eziosoft.moviesInParis.presentation.ui.movieDetailsBottomSheet.MovieDetailsBottomSheet
import com.eziosoft.moviesInParis.presentation.ui.rotating
import org.koin.androidx.compose.getViewModel

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val viewModel = getViewModel<ListScreenViewModel>()
    val state = viewModel.state
    val listState: LazyGridState = rememberLazyGridState()

    Box(
        modifier = modifier
    ) {
        when (state.dbState) {
            DBState.Unknown -> Unit
            DBState.Updating -> Updating()
            DBState.Ready -> SearchAndList(viewModel, state, listState)
        }
    }
}

@Composable
private fun Updating() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.rotating(3000),
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Updating"
            )
            Text("Updating Database...")
        }
    }
}

@Composable
private fun SearchAndList(
    viewModel: ListScreenViewModel,
    state: ScreenState,
    listState: LazyGridState
) {
    Column {
//        SearchBox(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp),
//            onSearch = {
//                viewModel.search(it)
//            }
//        )
        if (state.items.isNotEmpty() || state.isLoading) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize(),
                state = listState
            ) {
                items(state.items.size) { i ->
                    if (i >= state.items.size - 1 &&
                        !state.endReached &&
                        !state.isLoading
                    ) {
                        viewModel.loadNextItems()
                    }
                    val item = state.items[i]
                    ListItem(item, onClick = {
                        viewModel.showMovieDetails(
                            id = it,
                            content = {
                                MovieDetailsBottomSheet()
                            }
                        )
                    })
                }
                item {
                    if (state.isLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LinearProgressIndicator()
                        }
                    }
                }
            }
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "empty list"
                    )
                    Text("Not found")
                }
            }
        }
    }
}
