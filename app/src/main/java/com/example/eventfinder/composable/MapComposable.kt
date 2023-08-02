package com.example.eventfinder.composable

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eventfinder.model.EventModel
import com.example.eventfinder.viewmodel.MainViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MapComposable(private val navController: NavController, private val viewModel: MainViewModel) {

    @Composable
    fun MapScreen(currentLocation:Location?)
    {
        val devicePosition : LatLng = LatLng(currentLocation!!.latitude,
            currentLocation!!.longitude)
        val cameraPositionState = rememberCameraPositionState{
            position = CameraPosition.fromLatLngZoom(devicePosition, 12f)
        }

        //Map UI Settings
        val mapUiSettings by remember {
            mutableStateOf(
                MapUiSettings(zoomControlsEnabled = false)
            )
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings
        ) {
            viewModel.eventListState.value?.forEach { event->
                Marker(
                    state = MarkerState(LatLng(event.location_x, event.location_y)),
                    title = event.title,
                    snippet = event.place_name,
                    onInfoWindowClick = {
                        GlobalScope.launch(Dispatchers.Main) {
                            navController.navigate("eventDetail/" + event.id)
                        }
                    }
                )
            }

        }
    }


}