package com.sroo.astroobus.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.interfaces.IListener
import com.google.firebase.Timestamp

class BusTransaction(
    var transactionId:String,
    var busId: String,
    var destinationPoint: String,
    var startingPoint: String,
    var dateString: String,
    var timeString:String,
    var price: Number,
    var availableSeats: Number,
    var time: Timestamp
) :IListener{
    private var listener: ListenerRegistration? = null
    private lateinit var db: FirebaseFirestore
    private var busTransactionUpdateListener: BusTransactionUpdateListener? = null

    interface BusTransactionUpdateListener {
        fun onUpdate()
    }
    init {
        startListening()
    }
    fun setUpdateListener(listener: BusTransactionUpdateListener) {
        busTransactionUpdateListener = listener
    }
    override fun startListening() {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
        val collectionRef = db.collection("BusTransaction")
        val query = collectionRef.whereEqualTo("busId", busId).whereEqualTo("transactionId", transactionId)

        listener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                for (document in snapshot.documents) {
                    val newAvailableSeats = document.getLong("availableSeats") ?: 0 as Number
                    availableSeats = newAvailableSeats
                    Log.d("BusTransaction", "AVAIL SEATS" +availableSeats.toString())

                    busTransactionUpdateListener?.onUpdate()
                }
            }
        }
    }

    override fun stopListening() {
        listener?.remove()
    }

}