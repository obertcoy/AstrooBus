package com.sroo.astroobus.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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
    init {
        FirebaseInitializer.initialize()
        firebase = FirebaseInitializer.instance?.getDatabase()
        databaseReference = firebase!!.getReference("Users")
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
        val adapter = AdapterHelper()
        val userMap = adapter.UserToHashmap(user)

        user!!.uid?.let {
            databaseReference.child(it).setValue(userMap)
                .addOnSuccessListener {
                    Log.d("Register Repository", "Success")
                }
                .addOnFailureListener { error ->
                    Log.e("Register Repository", "Fail: ${error.message}", error)
                }
        }
    }


}