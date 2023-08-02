package com.example.eventfinder.model

import java.util.Date

data class EventModel(
    val id : Int,
    val categoryId : Int,
    val countyId : Int,
    val title : String,
    val event_date : Date,
    val place_name : String,
    val location_x : Double,
    val location_y : Double,
    val imageUrl : String,
    val description : String,
    val ticketPrice : Double,
    val address : String,
    var favId : Int,
    var fav_data: EventFavoriteModel?
)
