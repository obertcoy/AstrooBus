package com.sroo.astroobus.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.User

class LoginRepository (){
    private var auth: FirebaseAuth?
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
        auth = FirebaseInitializer.instance?.getAuth()
    }

    fun login(password: String, email: String, activity: Activity, callback: (User?) -> Unit) {
        val userRef = db.collection("Users")
            .whereEqualTo("email", email)

        userRef.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result?.documents
                    if (documents != null && !documents.isEmpty()) {
                        val userData = documents[0].data
                        val userEmail = userData?.get("email") as String
                        val userName = userData?.get("name") as String
                        val userPhoneNum = userData?.get("phoneNum") as String
                        val userRole = userData?.get("role") as String
                        val userId = userData?.get("uid") as String
                        val userPass = userData?.get("password") as String
                        if(password.equals(userPass)){
                            callback(User(userId,userName,userEmail, userPhoneNum, userPass, userRole))
                        }else{
                            callback(null)
                        }
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
    }

    fun getUserInfo(userId: String, callback: (User?) -> Unit) {
        val userRef = db.collection("Users")
            .whereEqualTo("uid", userId)

        userRef.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result?.documents
                    if (documents != null && !documents.isEmpty()) {
                        val userData = documents[0].data
                        val userEmail = userData?.get("email") as String
                        val userName = userData?.get("name") as String
                        val userPhoneNum = userData?.get("phoneNum") as String
                        val userRole = userData?.get("role") as String
                        val userId = userData?.get("uid") as String
                        val userPass = userData?.get("password") as String
                        callback(User(userId,userName,userEmail, userPhoneNum, userPass, userRole))
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
    }


}