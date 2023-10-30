package com.sroo.astroobus.`view-model`

import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.repository.BusRepository
import com.sroo.astroobus.repository.BusTransactionRepository
import java.util.ArrayList

class BusTransactionViewModel {
    private var repository = BusTransactionRepository()

    fun updateAvailableSeatNum(transactionId: String, decreaseSeat:Int){
        repository.updateAvailableSeatNum(transactionId, decreaseSeat)
    }

    fun deployBus(busTransaction: BusTransaction){
        repository.deployBus(busTransaction)
    }

    fun getAllBusTransaction(callback: (ArrayList<BusTransaction>) -> Unit) {
        repository.getAllBusTransaction(callback)
    }

}