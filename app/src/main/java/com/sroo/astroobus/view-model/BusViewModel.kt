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

    fun updateBusStatus(busId: String, status:String){
        repository.updateBusStatus(busId, status)
    }

    fun addBus(bus: Bus, ctx: Context, callback: (String) -> Unit){
        Log.d("BusViewModel", bus.busPlate)
        if(bus.busPlate == ""){
            UIHelper.createToast(ctx, "Fill in the bus plate")
            return
        }else if(bus.busPlate.length < 3){
            UIHelper.createToast(ctx, "Invalid plate number")
            return
        }else{
            repository.addBus(bus, callback)
        }
    }
}