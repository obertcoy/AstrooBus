package com.sroo.astroobus.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction

class BusRepository (){
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun getBus(busId: String, transactionId: String, callback: (Bus?) -> Unit) {
        val ref = db.collection("Bus")

        val query = ref.whereEqualTo("busId", busId).whereEqualTo("transactionId", transactionId)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                Log.d("BusRepository", busId + transactionId)
                if (documents != null && !documents.isEmpty()) {
                    val document = documents[0]
                    val data = document.data
                    val busId = data?.get("busId") as String
                    val busPlateNumber = data?.get("busPlateNumber") as String
                    val busStatus = data?.get("busStatus") as String
                    val seatString = data?.get("seatString") as String
                    val seats = data?.get("seats") as Number
                    val transactionId = data?.get("transactionId") as String

                    val bus = Bus(busId, busPlateNumber, seats, busStatus, seatString, transactionId)
                    Log.d("BusRepository", bus.seatString)
                    callback(bus)
                } else {
                    Log.d("BusRepository", "documents kosong")
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    }

}