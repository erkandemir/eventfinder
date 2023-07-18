package com.example.eventfinder.Model

import android.net.Uri
import java.util.Date

data class EventModel(
    val id : Int,
    val categoryId : Int,
    val countyId : Int,
    val title : String,
    val date : Date,
    val placeName : String,
    val locationX : Double,
    val locationY : Double,
    val imageUrl : String,
    val description : String,
    val ticketPrice : Double,
    val address : String
)

