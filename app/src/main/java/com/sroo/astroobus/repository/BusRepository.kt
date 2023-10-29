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

    fun updateBusSeats(seatString: String, seatNumber: List<Int>, busId: String){
        val parts = seatString.split("/").toMutableList()
        for(seat in seatNumber){
            val row = (seat - 1) / 4 + 2
            var seats = parts[row]
            var seats1 = seats.get(0).toString()
            var seats2 = seats.get(1).toString()
            var seats3 = seats.get(3).toString()
            var seats4 = seats.get(4).toString()

            val space = seats.get(2)
            val inputttedSeats = (seat - 1) % 4
            if(inputttedSeats == 0){
                seats1 = "R"
            }else if (inputttedSeats == 1){
                seats2 = "R"
            }else if(inputttedSeats == 2){
                seats3 = "R"
            }else if(inputttedSeats == 3){
                seats4 = "R"
            }
            val string = seats1 + seats2 + space + seats3 + seats4
            parts[row] = string
        }
        val modifiedSeatString = "/" + parts[1] +"/" + parts[2]+"/" + parts[3]+"/" + parts[4]+"/" + parts[5]+"/" + parts[6]+"/" + parts[7]+"/" + parts[8]+"/" + parts[9]
        Log.d("BusRepository", "MODIFIED SEAT:" + modifiedSeatString)
        val newSeats = hashMapOf(
            "seatString" to modifiedSeatString
        )

        db.collection("Bus")
            .document(busId)
            .update(newSeats as Map<String, Any>)
            .addOnSuccessListener {
                println("Document successfully updated!")
            }
            .addOnFailureListener { e ->
                println("Error updating document: $e")
            }

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