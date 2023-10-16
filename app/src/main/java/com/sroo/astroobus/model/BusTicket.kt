package com.sroo.astroobus.model

data class BusTicket (

    val ticketID: String,
    val fromLocation: String,
    val destinationLocation: String,
    val startTime: String,
    val endTime: String,
    val seats: Int,
    val price: Int,

)