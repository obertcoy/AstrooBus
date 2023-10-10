package com.sroo.astroobus.viewModel

import android.content.Context
import com.sroo.astroobus.model.User
import com.sroo.astroobus.repository.RegisterRepository

class RegisterViewModel() {
    private val repository = RegisterRepository()

    fun registerUser(user: User, context: Context, TAG: String){
        if(user.name == null|| user.password == null|| user.email == null|| user.phoneNum == null){

        }

        var id = repository.registerUser(user,context,TAG)
        if(id == null){
            //display error
        }else{
            //lanjut ke verif
        }
    }
}