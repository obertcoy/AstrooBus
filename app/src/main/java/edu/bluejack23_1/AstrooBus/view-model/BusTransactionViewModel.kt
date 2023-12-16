package edu.bluejack23_1.AstrooBus.`view-model`

import android.content.Context
import edu.bluejack23_1.AstrooBus.helper.UIHelper
import edu.bluejack23_1.AstrooBus.model.BusTransaction
import edu.bluejack23_1.AstrooBus.repository.BusTransactionRepository
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

    fun getUserOnGoingReservation(userId: String, callback: (ArrayList<BusTransaction>?) -> Unit){
        if(userId.equals("")){
            return
        }
        repository.getUserOnGoingReservation(userId, callback)
    }

}