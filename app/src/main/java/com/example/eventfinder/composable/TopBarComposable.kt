package com.example.eventfinder.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventfinder.viewmodel.MainViewModel

class TopBarComposable() {
    @Composable
    fun TopBar() {
        Box(modifier = Modifier.fillMaxWidth()
            .height(40.dp)) {
                Column(
                    Modifier.fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Event Finder", color = Color.Black,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primary)
                            .height(2.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
