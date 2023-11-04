package com.sroo.astroobus.`view-model`

import android.content.Context
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.repository.BusRepository
import com.sroo.astroobus.repository.BusTransactionRepository
import java.util.ArrayList

class BusTransactionViewModel {
    private var repository = BusTransactionRepository()

    fun updateAvailableSeatNum(transactionId: String, decreaseSeat:Int){
        repository.updateAvailableSeatNum(transactionId, decreaseSeat)
    }

    fun deployBus(busTransaction: BusTransaction, context: Context){
        if(busTransaction.startingPoint == "" || busTransaction.destinationPoint == ""){
            UIHelper.createToast(context,"Destination must be filled")
        }else if(busTransaction.dateString == "" || busTransaction.timeString == ""){
            UIHelper.createToast(context,"Invalid time")
        }else{
            repository.deployBus(busTransaction)
        }
    }

    fun getAllBusTransaction(callback: (ArrayList<BusTransaction>) -> Unit) {
        repository.getAllBusTransaction(callback)
    }

    fun deactivatePastBusTransactions() {
        repository.deactivatePastBusTransactions()
    }
    fun getAllTodayTransaction(date:String, callback: (ArrayList<BusTransaction>) -> Unit){
        repository.getAllTodayTransaction(date, callback)
    }

}