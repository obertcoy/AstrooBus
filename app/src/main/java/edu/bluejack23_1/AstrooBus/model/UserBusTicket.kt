package edu.bluejack23_1.AstrooBus.model

data class UserBusTicket (

    val ticketID: String,
    var destinationPoint: String,
    var startingPoint: String,
    val startTimeString: String,
    val endTimeString: String,
    val seat: Int,
    val price: Int,
    val dateString: String
)