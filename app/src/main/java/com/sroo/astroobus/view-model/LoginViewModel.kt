package com.sroo.astroobus.`view-model`

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.sroo.astroobus.activity.guest.GuestLoginActivity
import com.sroo.astroobus.activity.admin.AdminMainActivity
import com.sroo.astroobus.activity.user.UserMainActivity
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.repository.LoginRepository
import com.sroo.astroobus.utils.SessionManager

class LoginViewModel(private val view: GuestLoginActivity) {
    private val repository = LoginRepository()

    fun login(password:String, email:String, activity: Activity){
        if(password == "" || email == ""){
            UIHelper.createToast(activity, "All fields must be filled")
        }else{
            repository.login(password,email, activity){
                    result->
                if(result != null){
                    if(view.getIsChecked()){
                        val sessionManager = SessionManager(activity)
                        sessionManager.setCurrUser(result.uid!!, result.role)
                    }
                    val userMainIntent = Intent(activity, UserMainActivity::class.java)
                    val adminMainIntent = Intent(activity, AdminMainActivity::class.java)
                    if(result.role == "user"){
                        userMainIntent.putExtra("CURR_UID",result.uid!!)
                        userMainIntent.putExtra("CURR_ROLE","user")
                        activity.startActivity(userMainIntent)
                    }else{
                        adminMainIntent.putExtra("CURR_UID",result.uid!!)
                        adminMainIntent.putExtra("CURR_ROLE","admin")
                        activity.startActivity(adminMainIntent)
                    }

                }else{
                    UIHelper.createToast(activity, "Invalid Credentials")
                }
            }
        }
    }
}