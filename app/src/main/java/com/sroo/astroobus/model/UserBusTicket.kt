package com.sroo.astroobus.model

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