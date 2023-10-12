package com.sroo.astroobus.`view-model`

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.sroo.astroobus.activity.guest.GuestLoginActivity
import com.sroo.astroobus.activity.user.UserMainActivity
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.repository.LoginRepository

class LoginViewModel(private val view: GuestLoginActivity) {
    private val repository = LoginRepository()
    private val UIHelper = UIHelper()

    fun login(password:String, email:String, activity: Activity){
        if(password == "" || email == ""){
            UIHelper.createToast(activity, "All fields must be filled")
        }else{
            repository.login(password,email, activity){
                    result->
                if(result != ""){
                    val userMainIntent = Intent(activity, UserMainActivity::class.java)
                    activity.startActivity(userMainIntent)
                }else{
                    UIHelper.createToast(activity, "Invalid Credentials")
                }
            }
        }
    }
}