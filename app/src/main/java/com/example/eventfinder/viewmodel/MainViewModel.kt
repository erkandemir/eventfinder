package com.example.eventfinder.viewmodel

import android.annotation.SuppressLint
import android.provider.Settings
import android.widget.Toast
import com.example.eventfinder.model.EventCategoryModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.eventfinder.model.EventFavoriteModel
import com.example.eventfinder.model.EventModel
import com.example.eventfinder.repository.EventRepository
import kotlinx.coroutines.launch
import retrofit2.Response
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
    var dateSelected : MutableLiveData<Boolean> = MutableLiveData(false)
    var selectedDate : MutableLiveData<String> = MutableLiveData(LocalDateTime.now().toLocalDate().toString())
    //deviceId
    var deviceId : String = ""


    //event category state
    var eventCategoryListState : MutableState<MutableList<EventCategoryModel>> = mutableStateOf(mutableListOf())
    //event list state
    var eventListState : MutableState<MutableList<EventModel>?> = mutableStateOf(mutableListOf())
    //event detail state
    var eventDetailState : MutableState<EventModel?> = mutableStateOf(null)
    //favoriteEventList
    var favoriteEventListState : MutableState<MutableList<EventModel>?> = mutableStateOf(mutableListOf())
    //
    var favButtonState : MutableState<Boolean> = mutableStateOf(false)




    //fetch all categories from api
    private fun getEventCategories()
    {
        viewModelScope.launch {
            val response = EventRepository.getEventCategories()
            eventCategoryListState.value = response.body()!!
            eventCategoryListState.value.add(0, EventCategoryModel(0, "All Categories", "-"))
            getEvents()
        }

    }

    //fetch event by category
    fun getEvents()
    {
        viewModelScope.launch {
            val response = EventRepository.getEvents(selectedEventCategoryModel.value!!.id,
                    selectedDate.value!!, dateSelected.value!!, deviceId)

            if (response != null) {
                if(response.isSuccessful)
                    eventListState.value = response.body()!!
            }
        }
    }

    fun getEventDetail(eventId: Int)
    {
        viewModelScope.launch {
            val response = EventRepository.getEventDetail(eventId, deviceId)

            if (response != null) {
                if(response.isSuccessful) {
                    eventDetailState.value = response.body()!!
                    val format = SimpleDateFormat("dd-MMMM-yyyy")
                    eventDetailState.value!!.formatted_eventdate =  format.format(Date(eventDetailState.value?.event_date!!.time))
                    favButtonState.value = (eventDetailState.value!!.fav_data != null)
                }

            }
        }
    }


    //set to favorites
    fun setFavorite(eventId:Int)
    {
        val event = eventListState.value!!.firstOrNull() { it.id == eventId}
        if(event != null) {
            if(event!!.fav_data == null) {
                viewModelScope.launch {
                    var response = EventRepository.setFavorite(deviceId, eventId)
                    if(response.isSuccessful) {
                        event.fav_data = EventFavoriteModel(response.body()!!.id, deviceId, event.id)
                        favButtonState.value = true
                    }

                }
            }
            else {
                viewModelScope.launch {
                    EventRepository.deleteFavorite(event.fav_data!!.id)
                    event.fav_data = null
                    favButtonState.value = false
                }
            }
        }
        else {
            //event not in eventList, event delete from favorite
            viewModelScope.launch {
                val event = favoriteEventListState.value!!.firstOrNull(){it.id == eventId}
                if(event!!.fav_data != null) {
                    EventRepository.deleteFavorite(event.fav_data!!.id)
                    favButtonState.value = false
                }
            }
        }

    }


    //reset filter
    fun resetFilter()
    {
        selectedEventCategoryModel = MutableLiveData(
            EventCategoryModel(0, "All Categories", "")
        )
        dateSelected = MutableLiveData(false)
        selectedDate = MutableLiveData(LocalDateTime.now().toLocalDate().toString())
        getEvents()
    }


    fun getFavoriteEventList()
    {
        viewModelScope.launch {
            val response : Response<MutableList<EventModel>>? = EventRepository.getAllEvents(deviceId)
            if (response != null) {
                if(response.isSuccessful)
                    favoriteEventListState.value = response.body()!!
            }

        }
    }


}