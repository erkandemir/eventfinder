package com.example.eventfinder.api
import com.example.eventfinder.model.EventCategoryModel
import com.example.eventfinder.model.EventFavoriteModel
import com.example.eventfinder.model.EventModel
import retrofit2.Response
import retrofit2.http.*

interface ApiDataService {
    @GET("/eventcategory/")
    suspend fun getEventCategories(): Response<MutableList<EventCategoryModel>>

    @GET("/event")
    suspend fun getEvents(
        @Query("categoryId") categoryId: Int?,
        @Query("event_date") event_date: String?,
    ): Response<MutableList<EventModel>>

    @GET("/event/{eventId}")
    suspend fun getEventDetail(@Path("eventId") eventId: Int): Response<EventModel>

    @FormUrlEncoded
    @POST("/eventfavorite/")
    suspend fun setFavorite(@Field("userDeviceId") deviceId: String, @Field("eventId") eventId: Int)

    @GET("/eventfavorite")
    suspend fun getFavorite(
        @Query("userDeviceId") deviceId: String,
        @Query("eventId") eventId: Int?,
    ): Response<MutableList<EventFavoriteModel>>


}