package com.sroo.astroobus.model

class BusTransaction(
    var busId: String,
    var destinationPoint: String,
    var startingPoint: String,
    var date: String,
    var time:String,
    var price: Number,
    var availableSeats: Number
) {
}