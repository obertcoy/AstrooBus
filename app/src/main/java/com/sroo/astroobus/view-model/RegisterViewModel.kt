package com.sroo.astroobus.`view-model`

import android.content.Context
import android.util.Log
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.User
import com.sroo.astroobus.repository.RegisterRepository

class RegisterViewModel() {
    private val repository = RegisterRepository()
    val emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
    val UIHelper = UIHelper()

     fun registerTempUser(name:String, email:String, password:String, confPass:String, phoneNum:String, context: Context){
        if(name == null|| email == null || password == null|| confPass == null|| phoneNum == null){
            UIHelper.createToast(context, "All fields must be filled")
        }else if(password.length < 8){
            UIHelper.createToast(context, "Password must be atleast 8 characters")
        }else if (!password.matches(Regex(".*[a-zA-Z].*")) || !password.matches(Regex(".*\\d.*"))){
            UIHelper.createToast(context, "Password must be alphanumeric")
        }else if (password != confPass){
            UIHelper.createToast(context, "Password doesn't match")
        }else if(email.matches(emailRegex.toRegex())){
            UIHelper.createToast(context, "Invalid email")
        }
    }
}