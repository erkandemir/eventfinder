package com.example.eventfinder.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavController
import com.example.eventfinder.model.DummyData
import com.example.eventfinder.model.EventCategoryModel
import com.example.eventfinder.model.EventModel
import com.example.eventfinder.viewmodel.MainViewModel

class FavoritesComposable(private val navController: NavController, private val viewModel: MainViewModel) {
    @Composable
    fun FavoritesScreen()
    {
        SideEffect {
            viewModel.getFavoriteEventList()
        }
        EventListComposable(true, navController, viewModel)
            .ScreenEventList()
    }

}