package com.example.eventfinder.viewmodel

import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventfinder.model.EventCategoryModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.Observer
import com.example.eventfinder.model.EventFavoriteModel
import com.example.eventfinder.model.EventModel
import com.example.eventfinder.repository.EventRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class MainViewModel() : ViewModel() {
    init {
        getEventCategories()
        print("View model initialized....")
    }


    var selectedEventCategoryModel : MutableLiveData<EventCategoryModel> = MutableLiveData(
        EventCategoryModel(0, "All Categories", "")
    )

    //Set eventlist date state today
    var selectedDate : MutableLiveData<String> = MutableLiveData(LocalDateTime.now().toLocalDate().toString())
    //event category state
    var eventCategoryResponse : MutableState<MutableList<EventCategoryModel>> = mutableStateOf(mutableListOf())
    //event list state
    var eventListResponse : MutableState<MutableList<EventModel>> = mutableStateOf(mutableListOf())
    //favorite list
    var favoriteListResponse : MutableState<MutableList<EventFavoriteModel>> = mutableStateOf(mutableListOf())
    // in favorite
    var inFavorite : MutableState<Boolean> = mutableStateOf(false)
    //deviceId
    var deviceId : String = ""



    //fetch all categories from api
    private fun getEventCategories()
    {
        viewModelScope.launch {
            val response = EventRepository.getEventCategories()
            eventCategoryResponse.value = response.body()!!
            eventCategoryResponse.value.add(0, EventCategoryModel(0, "All Categories", "-"))
            getEvents()
        }

    }

    //fetch event by category
    fun getEvents()
    {
        viewModelScope.launch {
            val response = EventRepository.getEvents(selectedEventCategoryModel.value!!.id, selectedDate.value!!)
            eventListResponse.value = response.body()!!
        }
    }

    //set to favorites
    fun setFavorite(eventId:Int)
    {
        viewModelScope.launch {
           EventRepository.setFavorite(deviceId, eventId)
        }
    }

    //get favorites
    fun getFavorites(eventId: Int?)
    {
        inFavorite.value = false
        viewModelScope.launch {
            val response = EventRepository.getFavorite(deviceId, eventId)
            favoriteListResponse.value = response.body()!!
            if(eventId != null && response.body() != null && response.body()!!.size > 0)
                inFavorite.value = favoriteListResponse.value[0].eventId == eventId
        }
    }


}