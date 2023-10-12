package com.sroo.astroobus.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper

class LoginRepository (){
    private var auth: FirebaseAuth?
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
        auth = FirebaseInitializer.instance?.getAuth()
    }

    fun login(password:String, email:String, activity: Activity, callback: (String)-> Unit){
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth!!.currentUser?.uid.toString()
                    callback(user)
                } else {
                    val exception = task.exception
                    callback("")
                }
            }
    }

}