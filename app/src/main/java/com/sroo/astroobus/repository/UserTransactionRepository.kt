package com.sroo.astroobus.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper

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
}