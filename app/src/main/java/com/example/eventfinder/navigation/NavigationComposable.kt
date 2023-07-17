package com.example.eventfinder.navigation

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screen(val route: String, val name:String, val icon: ImageVector) {
    object navEvents : Screen("eventList", "Events", Icons.Default.Share)
    object navMap : Screen("map", "Map", Icons.Default.Place)
    object navFavorites : Screen("myEventList", "My Favorites", Icons.Default.Favorite)
}


@Composable
fun BottomNavigationComposable(navController: NavController)
{
    val backStackEntry = navController.currentBackStackEntryAsState()
    val items = listOf(
        Screen.navEvents,
        Screen.navMap,
        Screen.navFavorites
    )
    NavigationBar(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route)},
                label = { Text((item.name))},
                icon =  { Icon(item.icon, contentDescription = null)}
            )
        }
    }
}

