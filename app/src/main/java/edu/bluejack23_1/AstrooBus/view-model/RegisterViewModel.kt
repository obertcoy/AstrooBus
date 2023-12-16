package edu.bluejack23_1.AstrooBus.`view-model`

import android.app.Activity
import android.content.Intent
import edu.bluejack23_1.AstrooBus.activity.guest.GuestRegisterActivity
import edu.bluejack23_1.AstrooBus.activity.user.UserMainActivity
import edu.bluejack23_1.AstrooBus.helper.SMSHelper
import edu.bluejack23_1.AstrooBus.helper.UIHelper
import edu.bluejack23_1.AstrooBus.helper.VerificationCodeHelper
import edu.bluejack23_1.AstrooBus.model.User
import edu.bluejack23_1.AstrooBus.repository.RegisterRepository

class RegisterViewModel(private val view: GuestRegisterActivity) {
    private val repository = RegisterRepository()
    private val emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"

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
//                            view.showVerificationDialog("Check your messages")
//                            view.startTimer()
                        }
                    }
                }
            }
        }
     }

    fun resendCode(phoneNum: String, activity: Activity){
        val codeGenerator = VerificationCodeHelper()
        val code = codeGenerator.generateCode()
        repository.addTempUserToDatabase(phoneNum, code)
        val smsHelper = SMSHelper()
        smsHelper.sendSMSWithPermission(activity, code, phoneNum)
    }

    fun registerUser(activity: Activity, user: User, confPass: String){
        if(user.name == ""|| user.email == "" || user.password == ""|| confPass == ""|| user.phoneNum == ""){
            UIHelper.createToast(activity, "All fields must be filled")
        }else if(user.password.length < 8){
            UIHelper.createToast(activity, "Password must be atleast 8 characters")
        }else if (!user.password.matches(Regex(".*[a-zA-Z].*")) || !user.password.matches(Regex(".*\\d.*"))){
            UIHelper.createToast(activity, "Password must be alphanumeric")
        }else if (user.password != confPass){
            UIHelper.createToast(activity, "Password doesn't match")
        }else if(!user.email.matches(emailRegex.toRegex())){
            UIHelper.createToast(activity, "Invalid email")
        }else{
            repository.findUsedPhone(user.phoneNum, activity){
                    result->
                if(result == 1){
                    UIHelper.createToast(activity, "Phone is already used")
                }else{
                    repository.findUsedEmail(user.email, activity){
                            result->
                        if(result == 1){
                            UIHelper.createToast(activity, "Email is already used")
                        }else{
                            repository.registerUser(user, activity){
                                    result ->
                                if(result != 0){
                                    val homeIntent = Intent(view, UserMainActivity::class.java)
                                    homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    homeIntent.putExtra("CURR_UID",result.toString())
                                    homeIntent.putExtra("CURR_ROLE","user")
                                    view.startActivity(homeIntent)
                                }else{
                                    UIHelper.createToast(activity,"Failed to register")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
