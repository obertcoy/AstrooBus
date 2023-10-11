package com.sroo.astroobus.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FirebaseInitializer private constructor(){
    private var database: FirebaseDatabase

    private var auth: FirebaseAuth
    init{
        database = FirebaseDatabase.getInstance()

        auth = Firebase.auth
    }

    companion object{
        var instance: FirebaseInitializer? = null

        fun initialize(){
            if(instance == null){
                instance = FirebaseInitializer()
            }
        }
    }

    fun getDatabase(): FirebaseDatabase{
        return database
    }

    fun getAuth(): FirebaseAuth{
        return auth
    }
}