package com.example.eventfinder.composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eventfinder.navigation.Screen
import com.example.eventfinder.viewmodel.MainViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat

class EventDetailComposable(private val navController : NavController, private val viewModel : MainViewModel){
    @Composable
    fun EventDetailScreen(eventId: Int?, fromFav:Boolean = false)
    {
        SideEffect {
            viewModel.getEventDetail(eventId!!)
        }

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
                    model = viewModel.eventDetailState.value?.imageUrl,
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

                    Text("Title: " + viewModel.eventDetailState.value?.title)
                    Text("Date: " + viewModel.eventDetailState.value?.event_date.toString())
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp))
                    Text("Description: " + viewModel.eventDetailState.value?.description)
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Ticket Price: €" + viewModel.eventDetailState.value?.ticketPrice
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
                Button(onClick = {
                     navController.navigate(Screen.navMap.route)
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary)) {
                    Text(text = "Show in Map")
                }

                //Favorite button
                Button(
                    onClick = {
                        viewModel.setFavorite(viewModel.eventDetailState.value!!.id)
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary),
                    enabled = true
                )
                {
                    if(!viewModel.favButtonState.value)
                    {
                        Text(text = "Add To Favorites")
                    }
                    else {
                        Text(text = "Delete From Favorites")
                    }
                }
            }



        }
    }
}