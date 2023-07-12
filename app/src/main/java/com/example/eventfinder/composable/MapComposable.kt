package com.example.eventfinder.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


class MapComposable(private val navController: NavController) {

    @Composable
    fun MapScreen()
    {
        Column() {
            Text(text = "Map Compose")
        }
    }
}