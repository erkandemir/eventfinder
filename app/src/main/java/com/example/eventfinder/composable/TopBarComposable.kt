package com.example.eventfinder.composable

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventfinder.viewmodel.MainViewModel

class TopBarComposable() {
    @Composable
    fun TopBar(iconId : Int) {
            Row {
                Row() {
                    Image(
                        painterResource(id = iconId),
                        contentDescription = "",
                        Modifier.size(50.dp)
                    )

                    Text(
                        "Event Finder",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
    }
}
