package com.sroo.astroobus.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.User
import kotlinx.coroutines.tasks.await

class RegisterRepository (){
    private var firebase: FirebaseDatabase?
    private var auth: FirebaseAuth?
    private lateinit var databaseReference: DatabaseReference
    init {
        FirebaseInitializer.initialize()
        firebase = FirebaseInitializer.instance?.getDatabase()
        databaseReference = firebase!!.getReference("Users")
        auth = FirebaseInitializer.instance?.getAuth()
    }


      fun registerUser(user: User, context: Context, TAG: String){
        auth?.createUserWithEmailAndPassword(user.email, user.password)
            ?.addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"create user with email:success")
                    var id = auth?.currentUser?.uid.toString()
                    if(id == null){
                        Log.d("qqq", "id kosong")
                        var UIhelper = UIHelper()
                        UIhelper.createToast(context, "Email is not valid")
                    }else{
                        Log.d("qqqq", id)
                        user.uid = id
                        addUserToDatabase(user, context)
                    }
                } else {
                    Log.w(TAG,"create user with email:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

            }

    }

    fun addUserToDatabase(user: User, context: Context) {
        Log.d("wwwwww", "addUserToDatabase")

        val userMap = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "phoneNum" to user.phoneNum,

        )

        user!!.uid?.let {
            databaseReference.child(it).setValue(userMap)
                .addOnSuccessListener {
                    Log.d("qqqqq", "Success")
                }
                .addOnFailureListener { error ->
                    Log.e("qqqqq", "Fail: ${error.message}", error)
                }
        }
    }


}