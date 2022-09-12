package com.eziosoft.parisinnumbers.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eziosoft.parisinnumbers.presentation.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.ui.theme.ParisInNumbersTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val actionDispatcher: ActionDispatcher by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParisInNumbersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationController(
                        modifier = Modifier.padding(8.dp),
                        actionDispatcher = actionDispatcher
                    )
                }
            }
        }
    }
}
