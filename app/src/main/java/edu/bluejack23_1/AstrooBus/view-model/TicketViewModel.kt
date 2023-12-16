package edu.bluejack23_1.AstrooBus.`view-model`

import android.util.Log
import edu.bluejack23_1.AstrooBus.model.BusTransaction
import edu.bluejack23_1.AstrooBus.repository.TicketRepository

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