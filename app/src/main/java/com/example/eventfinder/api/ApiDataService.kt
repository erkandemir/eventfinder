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
        @Query("event_date__gte") event_date__gte: String?,
        @Query("event_date") event_date__exact: String?,
    ): Response<MutableList<EventModel>>

    @GET("/event/{eventId}/event_detail_fav/{userdeviceId}")
    suspend fun getEventFav(
        @Path("eventId") eventId: Int,
        @Path("userdeviceId") userdeviceId: String,
    ): Response<EventModel>


    @GET("/eventfavorite")
    suspend fun getFavorite(
        @Query("userDeviceId") deviceId: String,
        @Query("eventId") eventId: Int?,
    ): Response<MutableList<EventFavoriteModel>>

    @FormUrlEncoded
    @POST("/eventfavorite/")
    suspend fun setFavorite(@Field("userDeviceId") deviceId: String, @Field("eventId") eventId: Int)
            :Response<EventFavoriteModel>

    @DELETE("/eventfavorite/{favId}/")
    suspend fun  deleteFavorite(
        @Path("favId") favId: Int) : Response<Unit>


}