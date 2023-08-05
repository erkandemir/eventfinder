package com.example.eventfinder.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eventfinder.model.EventModel
import com.example.eventfinder.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*


class EventListComposable(private val favoriteMode : Boolean,
                          private val navController : NavController,
                          private val viewModel: MainViewModel
) {

    @Composable
    fun ScreenEventList()
    {
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(3000)
                refreshing = false
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = { refreshing = true },
        ) {

            Box() {
                Column {
                    if(favoriteMode)
                        FavoriteList()
                    else {
                        EventList()
                    }
                }
            }
        }

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
                    onClick = { navController.navigate("eventDetail/" + event.id) }
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Column(
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(event.title)
                            val date = Date(event.event_date.time)
                            val format = SimpleDateFormat("dd-MMMM-yyyy")
                            Text(format.format(date))

                        }
                    }
                }
        }
    }

    @Composable
    private fun FavoriteList()
    {
        val favEvents = viewModel.favoriteEventListState.value!!.filter { it.fav_data != null }
        if(favEvents.isNotEmpty()){
            LazyColumn {
                items(favEvents) { event ->
                    EventItem(event = event)
                }
            }
        }
        else {
            NoEventBox()
        }
    }

    @Composable
    private fun EventList() {
        FilterBarComposable(viewModel).FilterBar()
        SideEffect {
            viewModel.getEvents()
        }
        if(viewModel.eventListState.value!!.size == 0) {
            NoEventBox()
        }else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(viewModel.eventListState.value!!) { event ->
                    EventItem(event)
                }
            }
        }
    }

    @Composable
    private fun NoEventBox()
    {
        Box(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()){
            Column{
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 90.dp),
                    text = "No event found.."
                )
            }
        }
    }




  


}