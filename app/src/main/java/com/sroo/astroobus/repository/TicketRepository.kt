package com.sroo.astroobus.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.api.Billing.BillingDestination
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.BusTransaction

class TicketRepository (){
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun getAllBusByRoute(destination: String, startingPoint: String, date: String): Task<List<BusTransaction>> {
        val ref = db.collection("BusTransaction")
        val query = ref.whereEqualTo("startingPoint", startingPoint)
            .whereEqualTo("destinationPoint", destination).whereEqualTo("dateString", date)
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
                    val startTime = data?.get("time") as Timestamp
                    val dateString = data?.get("dateString") as String
                    val endTimeString = data?.get("endTimeString") as String
                    val endTime = data?.get("endTime") as Timestamp
                    BusTransaction(
                        transactionId,
                        busId,
                        destinationPoint,
                        startingPoint,
                        dateString,
                        timeString,
                        endTimeString,
                        startTime,
                        endTime,
                        price,
                        availableSeats
                    )
                }

                busTransactions ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}