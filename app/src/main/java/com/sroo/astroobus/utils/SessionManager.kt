package com.sroo.astroobus.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getCurrUser(): String? {
        return sharedPreferences.getString("currUser", "")
    }

    fun getCurrRole(): String?{
        return sharedPreferences.getString("currRole", "")
    }

    fun logout(){
        editor.putString("currUser", "")
        editor.putString("currRole", "")
        editor.apply()
    }

    fun setCurrUser(currUID: String, currRole:String) {
        editor.putString("currUser", currUID)
        editor.putString("currRole", currRole)
        editor.apply()
    }
}