package com.sroo.astroobus.`view-model`

import com.sroo.astroobus.model.HistoryTransaction
import com.sroo.astroobus.model.UserTransaction
import com.sroo.astroobus.repository.UserTransactionRepository
import java.util.ArrayList

class UserTransactionViewModel() {
    private var repository = UserTransactionRepository()

    fun addUserTransaction(transactionId:String, seatsNumber:String,totalPrice:Number, userId:String){
        repository.addUserTransaction(transactionId, seatsNumber, totalPrice, userId)
    }

    fun getUserTransaction(userId: String,  callback: (ArrayList<HistoryTransaction>) -> Unit) {
        repository.getUserTransaction(userId){
            result->
            if(result != null){
                callback(result)
            }
        }
    }
}