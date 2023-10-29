package com.sroo.astroobus.`view-model`

import android.content.Context
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.model.User
import com.sroo.astroobus.repository.AccountRepository

class AccountViewModel {
    private var repository = AccountRepository()

    fun updatePhoneNumber(userId: String, phoneNum:String, context: Context){
        if(userId == ""||phoneNum == ""){
            UIHelper.createToast(context, "Invalid Changes")
        }else{
            repository.updatePhoneNumber(userId,phoneNum)
        }
    }

    fun updateName(userId: String, name:String, context: Context){
        if(userId == ""||name == ""){
            UIHelper.createToast(context, "Invalid Changes")
        }else{
            repository.updateName(userId, name)
        }
    }

    fun getUserById(userId: String,  context: Context,callback: (User?) -> Unit){
        repository.getUserById(userId){
            result ->
            if(result != null){
                callback(result)
            }else{
                UIHelper.createToast(context, "Failed fetching data")
            }
        }
    }

}