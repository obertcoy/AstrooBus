package com.sroo.astroobus.repository

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.model.User

class AccountRepository {
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
    }

    fun updatePassword(userId:String, newPassword:String){

    }

    fun updateName(userId: String, name:String){
        val newName = hashMapOf(
            "name" to name
        )

        db.collection("Users")
            .document(userId)
            .update(newName as Map<String, Any>)
            .addOnSuccessListener {
                println("Document successfully updated!")
            }
            .addOnFailureListener { e ->
                println("Error updating document: $e")
            }
    }

    fun updateEmail(userId: String, oldEmail:String, newEmail: String){

    }

    fun updatePhoneNumber(userId: String, phoneNum:String){
        val newName = hashMapOf(
            "phoneNum" to phoneNum
        )

        db.collection("Users")
            .document(userId)
            .update(newName as Map<String, Any>)
            .addOnSuccessListener {
                println("Document successfully updated!")
            }
            .addOnFailureListener { e ->
                println("Error updating document: $e")
            }
    }

    fun getUserById(userId: String,  callback: (User?) -> Unit){
        val loginRepo = LoginRepository()
        return loginRepo.getUserInfo(userId, callback)
    }

//    fun update
}