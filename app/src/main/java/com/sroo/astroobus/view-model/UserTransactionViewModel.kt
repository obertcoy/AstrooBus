package com.sroo.astroobus.`view-model`

import com.sroo.astroobus.repository.UserTransactionRepository

class UserTransactionViewModel() {
    private var repository = UserTransactionRepository()

    fun addUserTransaction(transactionId:String, seatsNumber:ArrayList<String>,totalPrice:Number, userId:String){
        repository.addUserTransaction(transactionId, seatsNumber, totalPrice, userId)
    }
}