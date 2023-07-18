package com.example.eventfinder.composable

import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import com.example.eventfinder.Model.EventModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MapComposable(private val navController: NavController) {

    @Composable
    fun MapScreen(currentLocation:Location?, eventList: MutableList<EventModel>)
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
            Marker(
                state = MarkerState(position = devicePosition),
                title = "You",
                snippet = "You are here"
            )

            eventList.forEach { event->
                Marker(
                    state = MarkerState(LatLng(event.locationX, event.locationY)),
                    title = event.title,
                    snippet = event.placeName,
                    onInfoWindowClick = {
                        GlobalScope.launch(Dispatchers.Main) {
                            navController.navigate("eventDetail/" + event.id)
                        }
                    }
                    // { navController.navigate("eventDetail/" + event.id) }
                )
            }

        }
    }


}