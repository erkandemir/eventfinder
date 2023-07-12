package com.example.eventfinder


import android.app.ActivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventfinder.Model.DummyData
import com.example.eventfinder.Model.EventModel
import com.example.eventfinder.composable.EventDetailComposable
import com.example.eventfinder.composable.EventListComposable
import com.example.eventfinder.composable.FavoritesComposable
import com.example.eventfinder.composable.MapComposable
import com.example.eventfinder.navigation.BottomNavigationComposable
import com.example.eventfinder.navigation.Screen
import com.example.eventfinder.ui.theme.EventFinderTheme
import java.util.*
import kotlin.reflect.typeOf


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventFinderTheme {
                navScreen()
            }
        }
    }
}


@Composable
fun navScreen()
{
    var navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            Row() {
                Text("Event Finder") }
        } ,
        bottomBar = {
            BottomNavigationComposable(navController = navController)
        }

    ) {
        Surface(
            color =  MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(it)
        ) {
            setNavigationHost(navController)
        }
    }
}

@Composable
fun setNavigationHost(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Screen.navEvents.route) {
        composable(Screen.navEvents.route) { EventListComposable(true, navController)
            .EventList(events = GetEventList()) }
        composable("eventDetail/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            EventDetailComposable(navController).EventDetailScreen(backStackEntry.arguments?.getInt("eventId"))
        }
        composable(Screen.navMap.route) { MapComposable(navController).MapScreen() }
        composable(Screen.navFavorites.route) { FavoritesComposable(navController).FavoritesScreen()}
    }
}



@Composable
fun GetEventList() : MutableList<EventModel> {
    var eventList : MutableList<EventModel>   = DummyData().GetDummyEventList();
    return eventList;
}



//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
  //  EventFinderTheme {
        //GetEventList()
    //}
//}