package com.sroo.astroobus.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.model.HistoryTransaction
import com.sroo.astroobus.model.UserTransaction
import java.util.ArrayList

class UserTransactionRepository() {
    private val adapter = AdapterHelper()
    private lateinit var db: FirebaseFirestore

    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }
    fun addUserTransaction(transactionId:String, seatsNumber:String,totalPrice:Number, userId:String){
        val transaction = adapter.userTransactionHashMap(transactionId, seatsNumber, totalPrice, userId)
        val ref = db.collection("UserTransaction")

        ref.add(transaction)
            .addOnSuccessListener { documentReference ->
                Log.d("Register Repository", "Success Adding UserTransaction with ID: ${documentReference.id}")
            }
            .addOnFailureListener { error ->
                Log.e("Register Repository", "Fail Adding User: ${error.message}", error)
            }
    }

    fun getUserTransaction(userId: String, callback: (ArrayList<HistoryTransaction>) -> Unit) {
        val ref = db.collection("UserTransaction")
        val query = ref.whereEqualTo("userId", userId)

        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                val userTransactionList = ArrayList<HistoryTransaction>()

                if (documents != null && !documents.isEmpty()) {
                    val transactionRepository = BusTransactionRepository()

                    for (document in documents) {
                        val data = document.data
                        val seatsNumber = data?.get("seatsNumber") as String
                        val totalPrice = data?.get("totalPrice") as String
                        val transactionId = data?.get("transactionId") as String

                        transactionRepository.getTransactionById(transactionId) { result ->
                            if (result != null) {
                                val historyTransaction = HistoryTransaction(
                                    seatsNumber,
                                    totalPrice,
                                    transactionId,
                                    userId,
                                    result.dateString,
                                    result.startingPoint,
                                    result.destinationPoint,
                                    result.timeString
                                )
                                userTransactionList.add(historyTransaction)
                            } else {

                            }
                        }
                    }

                    callback(userTransactionList)
                } else {
                    Log.d("BusRepository", "documents kosong")
                }
            } else {
            }
        }
    }

}