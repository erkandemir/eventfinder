package com.example.eventfinder.model

import java.util.*
import kotlin.collections.ArrayList

class DummyData {
    fun GetDummyEventList() : MutableList<EventModel>
    {
        val e1 = EventModel(1, 1, 1,"Event1", Date(),
            "Place1",53.344198, -6.259729, "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8ZXZlbnR8ZW58MHx8MHx8fDA%3D&w=1000&q=80",
            " description 1", 20.50, " Dublin")
        val e2 = EventModel(2, 1, 1,"Event2", Date(),
            "Place1",53.341425, -6.264297,"https://media.istockphoto.com/id/479977238/tr/fotoğraf/table-setting-for-an-event-party-or-wedding-reception.jpg?s=612x612&w=0&k=20&c=qAMoRe6osR_ViuAIPpgxP24k7qNmZ72LNNMnSvLjWqg=",
            " description 1", 20.50,  " Dublin")
        val e3 = EventModel(3, 2, 1,"Event3", Date(),
            "Place1",53.342503, -6.299711,"https://supamamas.co.ke/wp-content/uploads/2022/06/FitFun-Event-Poster-With-Prices.jpg",
            " description 1", 20.50,  " Dublin")
        val e4 = EventModel(4, 2, 1,"Event4", Date(),
            "Place1",53.343429, -6.279014,  "https://img.freepik.com/premium-vector/music-event-banner-template-with-photo_52683-13693.jpg",
            " description 1", 20.50, " Dublin")
        val e5 = EventModel(5, 3, 1,"Event5", Date(),
            "Place1",53.348984, -6.207511, "https://thumbs.dreamstime.com/b/happy-co-workers-celebrating-company-party-corporate-event-happy-co-workers-celebrating-company-party-165727986.jpg",
            " description 1", 20.50,  " Dublin")
        val e6 = EventModel(6, 3, 1,"Event6", Date(),
            "Place1",53.357943, -6.303299, "https://blog.vantagecircle.com/content/images/2019/06/company-event.png",
            " description 1", 20.50,  " Dublin")
        var eventList : MutableList<EventModel> = ArrayList()
        eventList.add(e1)
        eventList.add(e2)
        eventList.add(e3)
        eventList.add(e4)
        eventList.add(e5)
        eventList.add(e6)

        return eventList;
    }

    fun GetEventDetail(eventId:Int?) : EventModel?
    {
        val eventList : MutableList<EventModel> = DummyData().GetDummyEventList()
        var event : EventModel? = eventList.firstOrNull { e-> e.id == eventId  }
        return event
    }
}