package edu.bluejack23_1.AstrooBus.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack23_1.AstrooBus.database.FirebaseInitializer
import edu.bluejack23_1.AstrooBus.helper.AdapterHelper
import edu.bluejack23_1.AstrooBus.model.User

class AccountRepository {
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    private var auth: FirebaseAuth?
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
        auth = FirebaseInitializer.instance?.getAuth()
    }

    fun updatePassword(userId: String, newPassword: String, callback: (String) -> Unit) {
        getUserById(userId) { result ->
            if (result != null) {
                if (newPassword == result.password) {
                    callback("error")
                } else {
                    val newPasswordData = hashMapOf(
                        "password" to newPassword
                    )

                    db.collection("Users")
                        .document(userId)
                        .update(newPasswordData as Map<String, Any>)
                        .addOnSuccessListener {
                            println("Document successfully updated!")
                            callback("success")
                        }
                        .addOnFailureListener { e ->
                            println("Error updating document: $e")
                            callback("error")
                        }
                }
            } else {
                callback("user_not_found")
            }
        }
    }


    fun updateName(userId: String, name:String){
        val newName = hashMapOf(
            "name" to name
        )
        db.collection("Users")
            .document(userId)
            .update(newName as Map<String, Any>)
            .addOnSuccessListener {
                Log.d("AccountRepository","Document successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.d("AccountRepository","Error updating document: $e")
            }
    }

    fun updateEmail(userId: String, newEmail: String){

        val newEmail = hashMapOf(
            "email" to newEmail
        )
        db.collection("Users")
            .document(userId)
            .update(newEmail as Map<String, Any>)
            .addOnSuccessListener {
                Log.d("AccountRepository","Document successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.d("AccountRepository","Error updating document: $e")
            }
    }

    fun updatePhoneNumber(userId: String, phoneNum:String){
        val newName = hashMapOf(
            "phoneNum" to phoneNum
        )

        db.collection("Users")
            .document(userId)
            .update(newName as Map<String, Any>)
            .addOnSuccessListener {
                Log.d("AccountRepository","Document successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.d("AccountRepository","Error updating document: $e")
            }
    }

    fun getUserById(userId: String,  callback: (User?) -> Unit){
        val loginRepo = LoginRepository()
        return loginRepo.getUserInfo(userId, callback)
    }

//    fun update
}