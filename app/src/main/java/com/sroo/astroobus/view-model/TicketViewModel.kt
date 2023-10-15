package com.sroo.astroobus.`view-model`

import com.google.android.gms.tasks.Task
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.repository.TicketRepository

class TicketViewModel (){

    private var ticketRepository = TicketRepository()
    fun getAllBusByRoute(destination: String, startingPoint: String, date: String): Task<List<BusTransaction>> {
        return ticketRepository.getAllBusByRoute(destination, startingPoint, date)
    }
}