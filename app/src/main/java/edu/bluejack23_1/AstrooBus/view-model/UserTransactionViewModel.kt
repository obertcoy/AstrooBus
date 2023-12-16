package edu.bluejack23_1.AstrooBus.`view-model`

import edu.bluejack23_1.AstrooBus.model.HistoryTransaction
import edu.bluejack23_1.AstrooBus.repository.UserTransactionRepository
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