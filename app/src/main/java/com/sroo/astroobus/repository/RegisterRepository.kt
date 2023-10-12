package com.sroo.astroobus.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.User
import kotlinx.coroutines.tasks.await

class RegisterRepository (){
    private var firebase: FirebaseDatabase?
    private var auth: FirebaseAuth?
    private lateinit var databaseReference: DatabaseReference
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        firebase = FirebaseInitializer.instance?.getDatabase()
        auth = FirebaseInitializer.instance?.getAuth()
    }


      fun registerUser(user: User, context: Context){
        auth?.createUserWithEmailAndPassword(user.email, user.password)
            ?.addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    Log.d("Register Repository","create user with email:success")
                    var id = auth?.currentUser?.uid.toString()
                    if(id == null){
                        Log.d("Register Repository", "ID NULL")
                        var UIhelper = UIHelper()
                        UIhelper.createToast(context, "Email is not valid")
                    }else{
                        user.uid = id
                        addUserToDatabase(user, context)
                    }
                } else {
                    Log.w("Register Repository","create user with email:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

            }

    }

    fun addUserToDatabase(user: User, context: Context) {
        //change to hashmap
        val userMap = adapter.UserToHashmap(user)
        databaseReference = firebase!!.getReference("Users")
        user!!.uid?.let {
            databaseReference.child(it).setValue(userMap)
                .addOnSuccessListener {
                    Log.d("Register Repository", "Success Adding User")
                }
                .addOnFailureListener { error ->
                    Log.e("Register Repository", "Fail Adding User: ${error.message}", error)
                }
        }
    }

    fun addTempUserToDatabase(phoneNum :String, code: String){
        val phoneMap = adapter.phoneHashMap(phoneNum, code)
        databaseReference = firebase!!.getReference("TempUsers")
        databaseReference.child(phoneNum).setValue(phoneMap)
            .addOnSuccessListener {
                Log.d("Register Repository", "Success Adding Temp User")
            }
            .addOnFailureListener { error ->
                Log.e("Register Repository", "Fail Adding Temp User: ${error.message}", error)
            }
    }

    fun findTempUserByPhoneNum(phoneNum: String):Int{
        databaseReference = firebase!!.getReference("TempUsers")
        try {
            val snapshot = databaseReference.child(phoneNum).get()

            if (snapshot != null) {
                val tempUserMap = snapshot.value as Map<String, Any>
                val code = tempUserMap["code"].toString()
                code // Return the code
            } else {
                null // No data exists for the phone number
            }
        } catch (e: Exception) {
            // Handle any exceptions that occur during the database operation
            Log.e("Retrieve Temp User Data", "Error: ${e.message}")
            null
        }
    }

    fun findUserBy(){

    }


}