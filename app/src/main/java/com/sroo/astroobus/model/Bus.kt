package com.sroo.astroobus.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.interfaces.IListener

class Bus(
    var busId: String,
    var busPlate: String,
    var busSeats: Number,
    var busStatus: String,
    var seatString: String,
    var transactionId: String
): IListener {
    private var listener: ListenerRegistration? = null
    private lateinit var db: FirebaseFirestore
    private var busUpdateListener: Bus.BusUpdateListener? = null

    interface BusUpdateListener {
        fun onUpdate(seatString: String)
    }

    init {
        startListening()
    }
    fun setUpdateListener(listener: Bus.BusUpdateListener) {
        busUpdateListener = listener
    }
    override fun startListening() {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
        val collectionRef = db.collection("Bus")
        val query = collectionRef.whereEqualTo("busId", busId)

        listener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                for (document in snapshot.documents) {
                    val newSeats = document.getString("seatString") ?: ""
                    seatString = newSeats

                    Log.d("Bus", seatString)

                    busUpdateListener?.onUpdate(seatString)
                }
            }
        }
    }

    override fun stopListening() {
        listener?.remove()
    }

}