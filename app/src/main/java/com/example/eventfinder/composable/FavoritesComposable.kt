package com.example.eventfinder.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.eventfinder.Model.DummyData
import com.example.eventfinder.Model.EventModel

class FavoritesComposable(private val navController: NavController) {
    @Composable
    fun FavoritesScreen()
    {
        var eventList : MutableList<EventModel>   = DummyData().GetDummyEventList();
        EventListComposable(false, navController)
            .ScreenEventList(events = eventList)
    }

}