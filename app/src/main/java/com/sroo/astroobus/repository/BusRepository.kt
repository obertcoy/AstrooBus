package com.sroo.astroobus.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.BusTransaction

class BusRepository (){
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun getSeatsNominal(busId: String, transactionId:String ): Task<List<BusTransaction>> {
        val ref = db.collection("Bus")
        val query = ref.whereEqualTo("busId", busId).whereEqualTo("transactionId", transactionId)
        return query.get().continueWith { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents

                val busTransactions = documents?.map { document ->
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
                    BusTransaction(transactionId,busId,destinationPoint,startingPoint,dateString,timeString,price,availableSeats,timestamp)
                }


                busTransactions ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}