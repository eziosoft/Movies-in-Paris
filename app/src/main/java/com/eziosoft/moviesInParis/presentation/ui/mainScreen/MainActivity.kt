package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.eziosoft.moviesInParis.presentation.ui.theme.ParisInNumbersTheme
import com.eziosoft.moviesInParis.presentation.ui.theme.PrimaryDark

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParisInNumbersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryDark
                ) {
                    MainScreen()
                }
            }
        }
    }
}
