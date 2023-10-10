package com.sroo.astroobus.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.model.User

class RegisterRepository (){
    private var firebase: DatabaseReference?
    private var auth: FirebaseAuth?
    init {
        FirebaseInitializer.initialize()
        firebase = FirebaseInitializer.instance?.getDatabaseRef()
        auth = FirebaseInitializer.instance?.getAuth()
    }


    fun registerUser(user: User, context: Context, TAG: String): String?{
        var id: String? = null

        auth?.createUserWithEmailAndPassword(user.email, user.password)
            ?.addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"createUserWithEmail:success")
                    id = auth?.currentUser?.uid

                } else {
                    Log.w(TAG,"createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

            }

        return id
    }

}