package com.sroo.astroobus.`view-model`

import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.repository.BusRepository
import com.sroo.astroobus.repository.BusTransactionRepository

class BusTransactionViewModel {
    private var repository = BusTransactionRepository()

    fun updateAvailableSeatNum(transactionId: String, decreaseSeat:Int){
        repository.updateAvailableSeatNum(transactionId, decreaseSeat)
    }

    fun deployBus(busTransaction: BusTransaction){
        repository.deployBus(busTransaction)
    }

}