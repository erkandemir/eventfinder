package com.example.eventfinder.repository

import BaseRepository
import com.example.eventfinder.api.ApiDataService
import com.example.eventfinder.model.EventCategoryModel
import com.example.eventfinder.model.EventFavoriteModel
import com.example.eventfinder.model.EventModel
import retrofit2.*

object EventRepository : BaseRepository<ApiDataService>(ApiDataService::class.java) {
    suspend fun getEventCategories() : Response<MutableList<EventCategoryModel>> {
        return client.getEventCategories()
    }

    suspend fun getEvents(_categoryId: Int, _event_date:String, _date_selected : Boolean, _deviceId: String?) : Response<MutableList<EventModel>> {
        var categoryId: Int? = null
        var eventdate_gte : String? = null
        var eventdate_exact : String? = null
        if(_categoryId > 0) categoryId = _categoryId
        if(!_date_selected) eventdate_gte = _event_date
        if(_date_selected)  eventdate_exact = _event_date
        return client.getEvents(categoryId, eventdate_gte, eventdate_exact, _deviceId)
    }

    suspend fun getAllEvents(userDeviceId:String?): Response<MutableList<EventModel>>
    {
        return client.getEvents(null, null, null, userDeviceId)
    }

    suspend fun getEventDetail(_eventId : Int, _deviceId: String) : Response<EventModel> {
        return client.getEventDetail(_eventId, _deviceId)
    }

    suspend fun setFavorite(_deviceId:String, _eventId: Int) : Response<EventFavoriteModel> {
        return client.setFavorite(_deviceId, _eventId)
    }

    suspend fun deleteFavorite(_favId : Int) {
        client.deleteFavorite(_favId)
    }

    suspend fun getFavorite(_deviceId:String, _eventId: Int?) : Response<MutableList<EventFavoriteModel>>{
        return client.getFavorite(_deviceId, _eventId)
    }
}