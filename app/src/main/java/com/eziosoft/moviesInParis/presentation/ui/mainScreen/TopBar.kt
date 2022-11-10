package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eziosoft.moviesInParis.presentation.ui.components.SearchBar

@Composable
fun TopBar(onSearch: (String) -> Unit) {
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        onSearch = {
            onSearch(it)
        }
    )
}
