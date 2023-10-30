package com.sroo.astroobus.`view-model`

import android.content.Context
import android.util.Log
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.repository.BusRepository
import com.sroo.astroobus.utils.LocationUtils
import javax.security.auth.callback.Callback

class BusViewModel() {
    private var repository = BusRepository()

    fun getBus(busId:String, transactionId:String,  callback: (Bus?) -> Unit) {
        repository.getBus(busId, transactionId){
            result->
            if (result != null){
                Log.d("BusViewModel", result.seatString)
                callback(result)
            }
        }
    }

    fun getAllBus( callback: (ArrayList<Bus>) -> Unit) {
        repository.getAllBus(callback)
    }

    fun updateBusSeats(seatString: String, seatNumber: List<Int>, busId: String){
        repository.updateBusSeats(seatString, seatNumber, busId)
    }

    fun deployBus(startingPoint: String, destinationPoint: String, startTime: String, endTime: String, ctx: Context){
        if (!(LocationUtils.checkLocation(startingPoint) && LocationUtils.checkLocation(
                destinationPoint
            ))
        ) {
            UIHelper.createToast(ctx, "Invalid location")
            return
        }

        if (startingPoint == "" || destinationPoint == "" || startTime == "" || endTime == "") {
            UIHelper.createToast(ctx, "All Fields Must Not Be Empty")
            return
        }

        if (startingPoint == destinationPoint) {
            UIHelper.createToast(ctx, "Invalid route")
            return
        }

        if(startTime >= endTime){
            UIHelper.createToast(ctx, "Invalid time")
            return
        }

        // deploy bus

    }

    fun addBus(bus: Bus, ctx: Context){

        if(bus.busPlate.isNotBlank()){
            UIHelper.createToast(ctx, "Fill in the bus plate")
            return
        }
        repository.addBus(bus)
        // add bus

    }
}