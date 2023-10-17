package com.sroo.astroobus.activity.guest

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.user.UserMainActivity
import com.sroo.astroobus.helper.SMSHelper
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.helper.VerificationCodeHelper
import com.sroo.astroobus.interfaces.ICounterable
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.model.User
import com.sroo.astroobus.`view-model`.RegisterViewModel
import java.time.chrono.ChronoLocalDate
import java.util.Locale


class GuestRegisterActivity : AppCompatActivity(), INavigable, ICounterable {

    private lateinit var dialog: Dialog
    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confPasswordEt: EditText
    private lateinit var phoneNumberEt: EditText
    private lateinit var registerBtn: Button
    private lateinit var registerViewModel: RegisterViewModel
    private var timerRunning: Boolean = false
    private val codeGenerator: VerificationCodeHelper = VerificationCodeHelper()
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeft: Long = 60000
    private lateinit var counterText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_register)
        dialog = Dialog(this)
        nameEt = findViewById(R.id.register_et_name)
        emailEt = findViewById(R.id.register_et_email)
        passwordEt = findViewById(R.id.register_et_password)
        confPasswordEt = findViewById(R.id.register_et_conf_password)
        phoneNumberEt = findViewById(R.id.register_et_phone)
        registerBtn = findViewById(R.id.register_btn_register)
        registerViewModel = RegisterViewModel(this)

        back(findViewById(R.id.register_back_arrow))
        verifyRegister(findViewById(R.id.register_btn_register))
    }

    private fun verifyRegister(registerBtn: Button){
        registerBtn.setOnClickListener{
            val phoneNumber = phoneNumberEt.text.toString()
            val email = emailEt.text.toString()
            val confPass = confPasswordEt.text.toString()
            val password = passwordEt.text.toString()
            val name = nameEt.text.toString()
            timeLeft = 60000

            val regisVM = RegisterViewModel(this)
            regisVM.registerTempUser(name, email, password, confPass,phoneNumber, this)
        }
    }

     fun showVerificationDialog(infoText: String) {
        dialog.setContentView(R.layout.dialog_verification)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.setCancelable(true)

         val backBtn = dialog.findViewById<ImageView>(R.id.verification_back_arrow)
         val infoTv = dialog.findViewById<TextView>(R.id.verification_tv_info)
         val submitBtn = dialog.findViewById<Button>(R.id.verification_submit)
         val inputText = dialog.findViewById<EditText>(R.id.verification_et)
         counterText = dialog.findViewById<TextView>(R.id.verification_counter)

        infoTv.text = infoText
        backBtn.setOnClickListener{
            countDownTimer.cancel()
            dialog.dismiss()
        }
//        backBtn.visibility = View.GONE

         counterText.setOnClickListener{
             if(timerRunning == false){
                 resetTimer()
                 val num = phoneNumberEt.text.toString()
                 registerViewModel.resendCode(num, this)
             }else{
                 UIHelper.createToast(this, "Resend code when the timer is done")
             }
         }


        submitBtn.setOnClickListener{
//            next(submitBtn)
            val phoneNumber = phoneNumberEt.text.toString()
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()
            val name = nameEt.text.toString()
            val code = inputText.text.toString()
            registerViewModel.verifyCode(this,code, User("",name,email,phoneNumber,password) ){
                result->
                if(result == true){
                    val homeIntent = Intent(this, UserMainActivity::class.java)
                    homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(homeIntent)
                }
            }

        }

        dialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 131) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val phoneNumber = phoneNumberEt.text.toString()
                val smsHelper = SMSHelper()
                val code = codeGenerator.generateCode()
                smsHelper.sendSMS(code, phoneNumber)
            }
        }
    }

    override fun next(nextBtn: View) {
        val loginIntent = Intent(this, GuestLoginActivity::class.java)
        startActivity(loginIntent)
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }

    override fun startTimer() {
        timerRunning = true
         this.countDownTimer = object : CountDownTimer(timeLeft, 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                timerRunning = false
//                updateTimer()
            }
        }
        countDownTimer.start()
    }

    override fun resetTimer() {
        timeLeft = 60000
        updateTimer()
    }

    override fun updateTimer() {
        var seconds:Int = (timeLeft / 1000).toInt()

        var str = String.format(Locale.getDefault(),"Resend code in %d", seconds)
        counterText.setText(str)

    }
}