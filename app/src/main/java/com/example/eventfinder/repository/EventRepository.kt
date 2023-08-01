package com.example.eventfinder.repository

import BaseRepository
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.eventfinder.api.Api
import com.example.eventfinder.api.ApiDataService
import com.example.eventfinder.api.IApiListener
import com.example.eventfinder.model.EventCategoryModel
import com.example.eventfinder.model.EventFavoriteModel
import com.example.eventfinder.model.EventModel
import retrofit2.*

object EventRepository : BaseRepository<ApiDataService>(ApiDataService::class.java) {
    suspend fun getEventCategories() : Response<MutableList<EventCategoryModel>> {
        return client.getEventCategories()
    }

    suspend fun getEvents(_categoryId: Int, _event_date:String) : Response<MutableList<EventModel>> {
        var categoryId: Int? = null
        var eventdate : String? = null
        if(_categoryId > 0) categoryId = _categoryId
        if(!_event_date.isEmpty()) eventdate = _event_date
        return client.getEvents(categoryId, eventdate)
    }

    suspend fun getEventDetail(_eventId : Int) : Response<EventModel> {
        return client.getEventDetail(_eventId)
    }

    suspend fun setFavorite(_deviceId:String, _eventId: Int) {
        client.setFavorite(_deviceId, _eventId)
    }

    suspend fun getFavorite(_deviceId:String, _eventId: Int?) : Response<MutableList<EventFavoriteModel>>{
        return client.getFavorite(_deviceId, _eventId)
    }
}