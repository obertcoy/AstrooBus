package com.sroo.astroobus.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.interfaces.IListener

class BusTransaction(
    var transactionId:String,
    var busId: String,
    var destinationPoint: String,
    var startingPoint: String,
    var dateString: String,
    var timeString:String,
    var time:Timestamp,
    var price: Number,
    var availableSeats: Number
) :IListener{
    private var listener: ListenerRegistration? = null
    private lateinit var db: FirebaseFirestore
    private var busTransactionUpdateListener: BusTransactionUpdateListener? = null

    interface BusTransactionUpdateListener {
        fun onUpdate(availableSeats: Number)
    }
    fun setUpdateListener(listener: BusTransactionUpdateListener) {
        busTransactionUpdateListener = listener
    }
    override fun startListening() {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
        val collectionRef = db.collection("BusTransaction")
        val query = collectionRef.whereEqualTo("busId", busId)

        listener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                for (document in snapshot.documents) {
                    val newAvailableSeats = document.getLong("availableSeats") ?: 0 as Number
                    availableSeats = newAvailableSeats

                    busTransactionUpdateListener?.onUpdate(availableSeats)
                }
            }
        }
    }

    override fun stopListening() {
        listener?.remove()
    }

}