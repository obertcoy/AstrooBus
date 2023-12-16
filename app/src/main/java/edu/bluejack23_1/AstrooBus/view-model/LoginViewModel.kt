package edu.bluejack23_1.AstrooBus.`view-model`

import android.app.Activity
import android.content.Intent
import edu.bluejack23_1.AstrooBus.activity.guest.GuestLoginActivity
import edu.bluejack23_1.AstrooBus.activity.admin.AdminMainActivity
import edu.bluejack23_1.AstrooBus.activity.user.UserMainActivity
import edu.bluejack23_1.AstrooBus.helper.UIHelper
import edu.bluejack23_1.AstrooBus.repository.LoginRepository
import edu.bluejack23_1.AstrooBus.utils.SessionManager

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
                    val adminMainIntent = Intent(activity, edu.bluejack23_1.AstrooBus.activity.admin.AdminMainActivity::class.java)
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