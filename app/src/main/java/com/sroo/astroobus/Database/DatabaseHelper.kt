package com.sroo.astroobus.Database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseHelper {
    private lateinit var database: DatabaseReference

    init{
        database = Firebase.database.reference
    }
}