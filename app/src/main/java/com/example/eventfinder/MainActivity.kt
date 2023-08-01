package com.example.eventfinder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventfinder.model.DummyData
import com.example.eventfinder.model.EventModel
import com.example.eventfinder.composable.*
import com.example.eventfinder.model.EventCategoryModel
import com.example.eventfinder.navigation.BottomNavigationComposable
import com.example.eventfinder.navigation.Screen
import com.example.eventfinder.ui.theme.EventFinderTheme
import com.example.eventfinder.viewmodel.MainViewModel
import com.google.android.gms.location.*
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {

    private var currentLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        setContent {
            val launcherMultiplePermissions = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionsMap ->
                val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                if (areGranted) {
                    // Use location
                } else {
                    // Show dialog
                }
            }
            checkAndRequestLocationPermissions(applicationContext, permissions, launcherMultiplePermissions)

            EventFinderTheme {
                navScreen()
            }
        }
    }


    @Composable
    fun checkAndRequestLocationPermissions(
        context: Context,
        permissions: Array<String>,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ) {
        if (
            permissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    currentLocation = location!!
                }
        } else {
            // Request permissions
            SideEffect {
                launcher.launch(permissions)
            }

        }
    }

    @Composable
    fun navScreen()
    {
        var navController: NavHostController = rememberNavController()
        Scaffold(
            topBar = {

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
    fun setNavigationHost(navController: NavHostController, viewModel: MainViewModel = viewModel())
    {
        viewModel.deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
        NavHost(navController = navController, startDestination = Screen.navEvents.route) {
            composable(Screen.navEvents.route) { EventListComposable(true, navController, viewModel)
                        .ScreenEventList()}
            composable("eventDetail/{eventId}",arguments = listOf(navArgument("eventId") { type = NavType.IntType })
            ) { backStackEntry ->
                EventDetailComposable(navController,viewModel).EventDetailScreen(backStackEntry.arguments?.getInt("eventId"))
            }
            composable(Screen.navMap.route) { MapComposable(navController, viewModel).MapScreen(currentLocation) }
            //composable(Screen.navFavorites.route) { FavoritesComposable(navController).FavoritesScreen(eventCategories)}
        }
    }

}







//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
  //  EventFinderTheme {
        //GetEventList()
    //}
//}