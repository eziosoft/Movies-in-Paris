package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eziosoft.moviesInParis.presentation.ui.components.SearchBox

@Preview
@Composable
fun TopBar() {
    SearchBox(modifier = Modifier.fillMaxWidth(), onSearch = {})
}