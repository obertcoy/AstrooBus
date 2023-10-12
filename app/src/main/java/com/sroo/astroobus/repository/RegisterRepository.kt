package com.sroo.astroobus.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterRepository (){
    private var auth: FirebaseAuth?
    private lateinit var db: FirebaseFirestore
    private val adapter = AdapterHelper()
    init {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
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
        val ref = user.uid?.let { db.collection("Users").document(it) }
        if (ref != null) {
            ref.set(userMap)
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
        val ref = db.collection("TempUsers").document(phoneNum)
        ref.set(phoneMap)
            .addOnSuccessListener {
                Log.d("Register Repository", "Success Adding Temp User")
            }
            .addOnFailureListener { error ->
                Log.e("Register Repository", "Fail Adding Temp User: ${error.message}", error)
            }
    }

    fun findTempUserByPhoneNum(phoneNum: String, context: Context):Int{
        val ref = db.collection("TempUsers").document(phoneNum)
        var flag = 0
        ref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    flag = 1
                }
            }
            .addOnFailureListener { exception ->
                flag = 1
            }

        return flag
    }

    fun findUsedEmail(email: String, context: Context, callback: (Int) -> Unit) {
        val query = db.collection("Users")
            .whereEqualTo("email", email)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    callback(1)
                } else {
                    callback(0)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore Query", "Error: ${exception.message}", exception)
                callback(0)
            }
    }

    fun findUsedPhone(phoneNum: String, context: Context, callback: (Int) -> Unit) {
        val query = db.collection("Users")
            .whereEqualTo("phoneNum", phoneNum)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    callback(1)
                } else {
                    callback(0)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore Query", "Error: ${exception.message}", exception)
                callback(0)
            }
    }
}