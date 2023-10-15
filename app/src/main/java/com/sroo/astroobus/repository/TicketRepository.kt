package com.sroo.astroobus.repository

import android.util.Log
import com.google.api.Billing.BillingDestination
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper

class TicketRepository (){
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun getAllBus(destination: String, startingPoint: String, date: String, callback){
        val ref = db.collection("TempUsers").document(phoneNum)
        ref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val code = document.get("code").toString()
                    callback(code)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore Query", "Error: ${exception.message}", exception)
            }
    }
}