package edu.bluejack23_1.AstrooBus.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FirebaseInitializer private constructor(){
    private var firestore: FirebaseFirestore

    private var auth: FirebaseAuth
    init{
        firestore = FirebaseFirestore.getInstance()
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

    fun getDatabase(): FirebaseFirestore{
        return firestore
    }

    fun getAuth(): FirebaseAuth{
        return auth
    }
}