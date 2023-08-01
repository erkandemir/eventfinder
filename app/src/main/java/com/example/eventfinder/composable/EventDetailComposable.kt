package com.example.eventfinder.composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eventfinder.viewmodel.MainViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat

class EventDetailComposable(private val navController : NavController, private val viewModel : MainViewModel){
    @Composable
    fun EventDetailScreen(eventId: Int?)
    {
        val event = viewModel.eventListResponse.value.firstOrNull() { it.id == eventId}
        //viewModel.getFavorites(event!!.id)

        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = event?.imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.LightGray)
                    .height(1.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(color = MaterialTheme.colorScheme.onPrimary))
            {
                Column(verticalArrangement = Arrangement.SpaceBetween) {

                    Text("Title: " + event?.title)
                    Text("Date: " + event?.event_date.toString())
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp))
                    Text("Description: " + event?.description)
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Ticket Price: €" + event?.ticketPrice
                    )
                }

            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.LightGray)
                    .height(1.dp)
            )
            
            Row(modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxHeight(0.2f)
            )
            {
                //Atandence button
                Button(onClick = { /*TODO*/ },
                modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary)) {
                    Text(text = "Attend to Event")
                }

                //Favorite button
                Button(
                    onClick = {
                        viewModel.setFavorite(event!!.id)
                        viewModel.inFavorite.value = true
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary),
                    enabled = !viewModel.inFavorite.value
                )
                {
                    if(!viewModel.inFavorite.value) {
                        Text(text = "Add To Fav")
                    }
                    else {
                        Text(text = "In favorites")
                    }
                }
            }



        }
    }
}