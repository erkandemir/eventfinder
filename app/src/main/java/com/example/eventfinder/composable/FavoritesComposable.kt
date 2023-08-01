package com.example.eventfinder.composable

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.eventfinder.model.DummyData
import com.example.eventfinder.model.EventCategoryModel
import com.example.eventfinder.model.EventModel
import com.example.eventfinder.viewmodel.MainViewModel

class FavoritesComposable(private val navController: NavController, private val viewModel: MainViewModel) {
    @Composable
    fun FavoritesScreen(eventCategories : MutableList<EventCategoryModel>)
    {
        var eventList : MutableList<EventModel>   = DummyData().GetDummyEventList();
        EventListComposable(false, navController, viewModel)
            .ScreenEventList()
    }

}