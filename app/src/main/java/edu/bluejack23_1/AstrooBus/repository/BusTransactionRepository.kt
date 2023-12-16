package edu.bluejack23_1.AstrooBus.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.AstrooBus.database.FirebaseInitializer
import edu.bluejack23_1.AstrooBus.helper.AdapterHelper
import edu.bluejack23_1.AstrooBus.model.BusTransaction
import java.util.ArrayList
import java.util.Date

class BusTransactionRepository {
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun getUserOnGoingReservation(userId: String, callback: (ArrayList<BusTransaction>?) -> Unit) {
        val userTransactionQuery = db.collection("UserTransaction").whereEqualTo("userId", userId)
        userTransactionQuery.get().addOnSuccessListener { userTransactions ->
            val ongoingBusTransactions = ArrayList<BusTransaction>()

            if (userTransactions.isEmpty) {
                callback(null)
            }

            var awaitingTransactions = userTransactions.size()

            for (userTransaction in userTransactions) {
                val transactionId = userTransaction.getString("transactionId")
                if (transactionId != null) {
                    val busTransactionQuery = db.collection("BusTransaction").whereEqualTo("transactionId", transactionId)
                    busTransactionQuery.get().addOnSuccessListener { busTransactions ->
                        for (busTransaction in busTransactions) {
                            val timestamp = busTransaction.getTimestamp("time")
                            if (timestamp != null && timestamp.toDate().after(Date())) {
                                val busId = busTransaction?.get("busId") as String
                                val availableSeats = busTransaction?.get("availableSeats") as Number
                                val destinationPoint = busTransaction?.get("destinationPoint") as String
                                val startingPoint = busTransaction?.get("startingPoint") as String
                                val price = busTransaction?.get("price") as Number
                                val transactionId = busTransaction?.get("transactionId") as String
                                val timeString = busTransaction?.get("timeString") as String
                                val dateString = busTransaction?.get("dateString") as String
                                val timestamp = busTransaction?.get("time") as Timestamp
                                val busTransactionObj = BusTransaction(transactionId, busId, destinationPoint, startingPoint, dateString, timeString, price.toInt(), availableSeats.toInt(), timestamp)
                                ongoingBusTransactions.add(busTransactionObj)
                            }
                        }

                        awaitingTransactions--

                        if (awaitingTransactions <= 0) {
                            callback(ongoingBusTransactions)
                        }

                    }.addOnFailureListener { e ->
                        callback(null)
                    }
                } else {
                    awaitingTransactions--
                }
                if (awaitingTransactions <= 0) {
                    callback(null)
                }
            }
        }.addOnFailureListener { e ->
            callback(null)
        }
    }


    fun getAllTodayTransaction(date:String, callback: (ArrayList<BusTransaction>) -> Unit){
        val ref = db.collection("BusTransaction").whereEqualTo("dateString", date)

        ref.get().addOnCompleteListener { task ->
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
                val busTransactionList = ArrayList(busTransactions ?: emptyList())
                Log.d("BusTransactionRepository", busTransactionList.size.toString())
                callback(busTransactionList)
            } else {
                Log.e("BusTransactionRepository", "Firestore query failed", task.exception)
                callback(ArrayList())
            }

        }
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

    fun deployBus(busTransaction: BusTransaction){
        val transaction = adapter.busTransactionToHashmap(busTransaction)
        val ref = db.collection("BusTransaction")

        ref.add(transaction)
            .addOnSuccessListener { documentReference ->
                Log.d("Bus Repository", "Success Adding Bus")
                val addAttr = hashMapOf(
                    "transactionId" to documentReference.id
                )
                db.collection("BusTransaction")
                    .document(documentReference.id)
                    .update(addAttr as Map<String, Any>)
                    .addOnSuccessListener {
                        println("Document successfully updated!")
                    }
                    .addOnFailureListener { e ->
                        println("Error updating document: $e")
                    }
                db.collection("Bus")
                    .document(busTransaction.busId)
                    .update(addAttr as Map<String, Any>)
                    .addOnSuccessListener {
                        println("Document successfully updated!")
                    }
                    .addOnFailureListener { e ->
                        println("Error updating document: $e")
                    }
            }
            .addOnFailureListener { error ->
                Log.e("Bus Repository", "Fail Adding Bus", error)
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

    fun getAllBusTransaction(callback: (ArrayList<BusTransaction>) -> Unit) {
        val ref = db.collection("BusTransaction").whereEqualTo("status", "Active")

        ref.get().addOnCompleteListener { task ->
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
                val busTransactionList = ArrayList(busTransactions ?: emptyList())
                Log.d("BusTransactionRepository", busTransactionList.size.toString())
                callback(busTransactionList)
            } else {
                Log.e("BusTransactionRepository", "Firestore query failed", task.exception)
                callback(ArrayList())
            }

        }
    }

    fun deactivatePastBusTransactions() {
        val busTransactionCollection = db.collection("BusTransaction")
        val busCollection = db.collection("Bus")

        busTransactionCollection.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val timestamp = document.get("time") as? Timestamp
                    val status = document.get("status") as String
                    val currentTime = Timestamp.now()

                    if (timestamp != null && timestamp.seconds < currentTime.seconds && status.equals("Active")) {
                        Log.d("BusTransactionRepository","Timestamp" + timestamp.seconds.toString())
                        Log.d("BusTransactionRepository", "Curr time" + currentTime.seconds.toString())
                        busTransactionCollection.document(document.id).update("status", "Unactive").addOnSuccessListener {
                            val busId = document.getString("busId")
                            if (busId != null) {
                                Log.d("BusTransactionRepository", "delete Transaction Id")
                                busCollection.document(busId).update("transactionId", "")
                            }
                        }
                    }
                }
            } else {
                println("Error getting documents: ${task.exception}")
            }
        }
    }


}