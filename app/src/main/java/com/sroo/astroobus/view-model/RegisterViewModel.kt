package com.sroo.astroobus.`view-model`

import android.content.Context
import android.util.Log
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.User
import com.sroo.astroobus.repository.RegisterRepository

class RegisterViewModel() {
    private val repository = RegisterRepository()
    val UIHelper = UIHelper()

     fun registerUser(user: User, context: Context){
        if(user.name == null|| user.password == null|| user.email == null|| user.phoneNum == null){
            UIHelper.createToast(context, "All fields must be filled")
        }else{
            repository.registerUser(user,context)
        }
    }
}