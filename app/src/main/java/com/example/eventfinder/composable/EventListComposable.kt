package com.example.eventfinder.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.example.eventfinder.model.EventModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eventfinder.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EventListComposable(private val isGrid : Boolean,
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
                Column() {
                    FilterBarComposable(viewModel).FilterBar()
                    val events = viewModel.eventListResponse.value
                    if(isGrid)
                        EventGrid(events = events)
                    else
                        EventColumn(events = events)
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
                            Text(event.place_name)
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
        if(events.size == 0) {
            Box(modifier = Modifier.height(200.dp).fillMaxWidth()){
                Column{
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(top = 90.dp),
                        text = "No event found.."
                    )
                }
            }
        }else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(events) { event ->
                    EventItem(event)
                }
            }
        }
    }




  


}