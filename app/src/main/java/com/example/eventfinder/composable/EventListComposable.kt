package com.example.eventfinder.composable

import android.service.autofill.OnClickAction
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.example.eventfinder.Model.EventModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eventfinder.Model.DummyData


class EventListComposable(private val isGrid : Boolean,
                          private val navController : NavController
) {
    @Composable
    fun ScreenEventList(events : MutableList<EventModel>)
    {
        if(isGrid)
            EventGrid(events = events)
        else
            EventColumn(events = events)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun EventItem(event : EventModel)
    {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp),
            modifier = Modifier
                .padding(10.dp)
                .height(250.dp)
                .combinedClickable(
                    onClick = { navController.navigate("eventDetail/" + event.id)}
                ),

        ) {

             Column(
                    modifier = Modifier.background(color  = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Row(modifier = Modifier.height(180.dp)) {
                        AsyncImage(
                            model = event.imageUrl,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row{
                        Column(
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(event.title)
                            Text(event.description)
                        }
                    }

                }

        }
    }

    @Composable
    private fun EventColumn(events : MutableList<EventModel>)
    {
        LazyColumn {
            items(events) { event ->
                EventItem(event = event)
            }
        }
    }

    @Composable
    private fun EventGrid(events: MutableList<EventModel>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(events) { event ->
                EventItem(event)
            }
        }
    }




  


}