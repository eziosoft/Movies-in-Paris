package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eziosoft.moviesInParis.presentation.ui.components.SearchBox

@Composable
fun TopBar(onSearch: (String) -> Unit) {
    SearchBox(
        modifier = Modifier.fillMaxWidth(),
        onSearch = {
            onSearch(it)
        }
    )
}
