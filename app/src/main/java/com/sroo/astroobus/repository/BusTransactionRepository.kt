package com.sroo.astroobus.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction

class BusTransactionRepository {
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun updateAvailableSeatNum(transactionId: String, decreaseSeat:Int){
       this.getTransactionById(transactionId){
           result->
           if(result != null){
               val seats = result.availableSeats.toInt() - decreaseSeat

               val newSeats = hashMapOf(
                   "availableSeats" to seats
               )

               db.collection("BusTransaction")
                   .document(transactionId)
                   .update(newSeats as Map<String, Any>)
                   .addOnSuccessListener {
                       println("Document successfully updated!")
                   }
                   .addOnFailureListener { e ->
                       println("Error updating document: $e")
                   }
           }
       }

    }

    fun getTransactionById(transactionId: String,  callback: (BusTransaction) -> Unit){
        val ref = db.collection("BusTransaction")

        val query = ref.whereEqualTo("transactionId", transactionId)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                if (documents != null && !documents.isEmpty()) {
                    val document = documents[0]
                    val data = document.data
                    val busId = data?.get("busId") as String
                    val availableSeats = data?.get("availableSeats") as Number
                    val destinationPoint = data?.get("destinationPoint") as String
                    val startingPoint = data?.get("startingPoint") as String
                    val price = data?.get("price") as Number
                    val transactionId = data?.get("transactionId") as String
                    val timeString = data?.get("timeString") as String
                    val dateString = data?.get("dateString") as String
                    val timestamp = data?.get("time") as Timestamp
                    val trans = BusTransaction(transactionId,busId,destinationPoint,startingPoint,dateString,timeString,price,availableSeats,timestamp)
                    callback(trans)
                } else {
                    Log.d("BusRepository", "documents kosong")
                }
            } else {

            }
        }
    }

}