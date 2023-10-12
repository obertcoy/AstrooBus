package com.sroo.astroobus.`view-model`

import android.app.Activity
import android.content.Context
import android.util.Log
import com.sroo.astroobus.activity.guest.GuestRegisterActivity
import com.sroo.astroobus.helper.SMSHelper
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.helper.VerificationCodeHelper
import com.sroo.astroobus.model.User
import com.sroo.astroobus.repository.RegisterRepository

class RegisterViewModel(private val view: GuestRegisterActivity) {
    private val repository = RegisterRepository()
    val emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
    val UIHelper = UIHelper()

     fun registerTempUser(name:String, email:String, password:String, confPass:String, phoneNum:String, activity: Activity){
        if(name == ""|| email == "" || password == ""|| confPass == ""|| phoneNum == ""){
            UIHelper.createToast(activity, "All fields must be filled")
        }else if(password.length < 8){
            UIHelper.createToast(activity, "Password must be atleast 8 characters")
        }else if (!password.matches(Regex(".*[a-zA-Z].*")) || !password.matches(Regex(".*\\d.*"))){
            UIHelper.createToast(activity, "Password must be alphanumeric")
        }else if (password != confPass){
            UIHelper.createToast(activity, "Password doesn't match")
        }else if(!email.matches(emailRegex.toRegex())){
            UIHelper.createToast(activity, "Invalid email")
        }else{
            repository.findUsedPhone(phoneNum, activity){
                result->
                if(result == 1){
                    UIHelper.createToast(activity, "Phone is already used")
                }else{
                    repository.findUsedEmail(email, activity){
                            result->
                        if(result == 1){
                            UIHelper.createToast(activity, "Email is already used")
                        }else{
                            val registerView = GuestRegisterActivity()
                            val codeGenerator = VerificationCodeHelper()
                            val code = codeGenerator.generateCode()
                            repository.addTempUserToDatabase(phoneNum, code)
                            val smsHelper = SMSHelper()
                            smsHelper.sendSMSWithPermission(activity, code, phoneNum)
                            view.showVerificationDialog("Check your messages")
                        }
                    }
                }
            }
        }
     }

    fun verifyCode(activity: Activity, code:String, user: User){
        repository.findTempUserCode(user.phoneNum, activity){
            result->
            if(code == result){
                repository.registerUser(user, activity)
            }else{
                UIHelper.createToast(activity,"Invalid code")
            }
        }
    }

}