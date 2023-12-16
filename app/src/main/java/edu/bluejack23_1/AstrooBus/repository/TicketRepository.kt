package edu.bluejack23_1.AstrooBus.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.AstrooBus.database.FirebaseInitializer
import edu.bluejack23_1.AstrooBus.helper.AdapterHelper
import edu.bluejack23_1.AstrooBus.model.BusTransaction

class TicketRepository (){
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun getAllBusByRoute(startingPoint: String, destination: String, date: String, callback: (ArrayList<BusTransaction>) -> Unit) {
       Log.d("TicketRepository", startingPoint + destination + date)
        val ref = db.collection("BusTransaction")
        val query = ref.whereEqualTo("startingPoint", startingPoint)
            .whereEqualTo("destinationPoint", destination)
            .whereEqualTo("dateString", date).whereEqualTo("status", "Active")

        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("TicketRepository", "Successful")
                val documents = task.result?.documents
                if (documents != null) {
                    Log.d("TicketRepository", documents.size.toString())
                }
                val busTransactions = documents?.map { document ->
                    val data = document.data
                    Log.d("TicketRepository", data?.get("busId").toString())
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
                val busTransactionList = ArrayList(busTransactions ?: emptyList())
                Log.d("TicketRepository", busTransactionList.size.toString())
                callback(busTransactionList)
            } else {
                // Handle query error
                Log.e("TicketRepository", "Firestore query failed", task.exception)
                callback(ArrayList())
            }
        }
    }

}