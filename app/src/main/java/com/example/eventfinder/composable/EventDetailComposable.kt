package com.example.eventfinder.composable

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.eventfinder.Model.DummyData

import com.example.eventfinder.Model.EventModel

class EventDetailComposable(private val navController : NavController){
    @Composable
    fun EventDetailScreen(eventId: Int?)
    {
        val event = DummyData().GetEventDetail(eventId)
        Column(modifier = Modifier.fillMaxHeight())
        {
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = event?.imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(color = MaterialTheme.colorScheme.onPrimary))
            {
                Column() {
                    Text("Title: " + event?.title)
                    Text("Date: " + event?.date)
                    Text("Description: " + event?.description)
                }

            }
            Row(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            {
                Button(onClick = { /*TODO*/ },
                modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary)) {
                    Text(text = "Attend to Event")
                }

                Button(onClick = { /*TODO*/ },
                    modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary)) {
                    Text(text = "Add To Fav")
                }
            }



        }
    }
}