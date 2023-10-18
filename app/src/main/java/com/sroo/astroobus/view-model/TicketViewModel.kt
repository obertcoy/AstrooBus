package com.sroo.astroobus.`view-model`

import android.util.Log
import com.google.android.gms.tasks.Task
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.repository.TicketRepository

class TicketViewModel (){

    private var ticketRepository = TicketRepository()
    fun getAllBusByRoute(startingPoint: String,destination: String,  date: String, callback: (ArrayList<BusTransaction>) -> Unit) {
        ticketRepository.getAllBusByRoute(startingPoint, destination, date)
            { result ->
                if (result != null) {
                    val busTransactions = result
                    Log.d("TicketViewModel", busTransactions.size.toString())
                    callback(busTransactions ?: ArrayList())
                } else {
                    callback(ArrayList())
                }
            }
    }


}