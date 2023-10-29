package com.sroo.astroobus.model

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.sroo.astroobus.database.FirebaseInitializer
import com.sroo.astroobus.interfaces.IListener

class User (
    var uid: String?,
    var name: String,
    var email: String,
    var phoneNum: String,
    var password:String,
    var role:String
    ): IListener {
    private var listener: ListenerRegistration? = null
    private lateinit var db: FirebaseFirestore
    private var userUpdateListener: User.UserUpdateListener? = null
    interface UserUpdateListener {
        fun onUpdate(user:User)
    }

    init {
        startListening()
    }
    fun setUpdateListener(listener: User.UserUpdateListener) {
        userUpdateListener = listener
    }
    override fun startListening() {
        FirebaseInitializer.initialize()
        db = FirebaseInitializer.instance?.getDatabase()!!
        val collectionRef = db.collection("Users")
        val query = collectionRef.whereEqualTo("uid", uid)

        listener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                for (document in snapshot.documents) {
                    val userData = document
                    val userEmail = userData?.get("email") as String
                    val userName = userData?.get("name") as String
                    val userPhoneNum = userData?.get("phoneNum") as String
                    val userRole = userData?.get("role") as String
                    val userId = userData?.get("uid") as String
                    val userPass = userData?.get("password") as String
                    userUpdateListener?.onUpdate(User(userId,userName,userEmail, userPhoneNum, userPass, userRole))
                }
            }
        }
    }

    override fun stopListening() {
        listener?.remove()
    }

}