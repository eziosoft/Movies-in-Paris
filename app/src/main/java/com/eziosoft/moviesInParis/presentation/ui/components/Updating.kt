package com.eziosoft.moviesInParis.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.eziosoft.moviesInParis.presentation.ui.rotating
import com.eziosoft.parisinnumbers.R

@Composable
fun Updating() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.rotating(3000),
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(R.string.updating)
            )
            Text(stringResource(R.string.updating_database))
        }
    }
}
