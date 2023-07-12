package com.example.eventfinder.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

class FavoritesComposable(private val navController: NavController) {
    @Composable
    fun FavoritesScreen()
    {
        Column() {
            Text("Favorites")
        }

    }
}