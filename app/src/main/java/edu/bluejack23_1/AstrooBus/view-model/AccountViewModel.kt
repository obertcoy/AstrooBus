package edu.bluejack23_1.AstrooBus.`view-model`

import android.content.Context
import edu.bluejack23_1.AstrooBus.helper.UIHelper
import edu.bluejack23_1.AstrooBus.model.User
import edu.bluejack23_1.AstrooBus.repository.AccountRepository

class AccountViewModel {
    private var repository = AccountRepository()
    private val emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"

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

    fun updatePassword(newPassword:String, oldPassword:String, confirmPassword:String, userId: String, context: Context){
        if(newPassword.equals("") || oldPassword.equals("") || confirmPassword.equals("")) {
            UIHelper.createToast(context, "All fields must be filled")
        }else if(!newPassword.equals(confirmPassword)){
            UIHelper.createToast(context, "Password not the same")
        }else{
            val str = repository.updatePassword(userId, newPassword){
                result->
                if(result.equals("error")){
                    UIHelper.createToast(context, "Invalid password")
                }else if(result.equals("success")){
                    UIHelper.createToast(context, "Password updated")
                }
            }
        }
    }

    fun updateEmail(userId: String, newEmail: String, context: Context) {
        if(userId == ""||newEmail.equals("")){
            UIHelper.createToast(context, "All field must be filled")
        }else if(!newEmail.matches(emailRegex.toRegex())){
            UIHelper.createToast(context, "Invalid email")
        }else{
            repository.updateEmail(userId, newEmail)
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