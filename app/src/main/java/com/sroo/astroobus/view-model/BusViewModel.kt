package com.sroo.astroobus.`view-model`

import android.util.Log
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.repository.BusRepository
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
}