package com.example.eventfinder

import com.example.eventfinder.model.EventModel
import org.junit.Test
import com.example.eventfinder.repository.*
import org.junit.Assert.*
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EventFinderUnitTest {
    @Test
    fun webApi_isAccessable() {
        val deviceId = "3b9fee7e76c21a61"
        val response: Response<List<EventModel>> =  EventRepository.client.getEventsForTest(deviceId).execute()
        assertTrue(response.isSuccessful && response.body()?.isNotEmpty() == true)
    }
}