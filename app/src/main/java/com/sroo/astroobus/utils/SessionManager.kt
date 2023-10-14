package com.sroo.astroobus.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getCurrUser(): String? {
        return sharedPreferences.getString("currUser", "")
    }

    fun setCurrUser(currUID: String) {
        editor.putString("currUser", currUID)
        editor.apply()
    }
}