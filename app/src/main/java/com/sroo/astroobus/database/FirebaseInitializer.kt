package com.sroo.astroobus.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseInitializer private constructor(){
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    init{
        database = Firebase.database.reference
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

    fun getDatabaseRef(): DatabaseReference{
        return database;
    }

    fun getAuth(): FirebaseAuth{
        return auth;
    }
}