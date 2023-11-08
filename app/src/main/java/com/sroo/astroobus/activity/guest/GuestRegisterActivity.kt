package com.sroo.astroobus.activity.guest

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
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
    private val handler = Handler(Looper.getMainLooper())
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
        registerUser(findViewById(R.id.register_btn_register))
    }

    private fun registerUser(submitBtn: Button){
        submitBtn.setOnClickListener{
            val phoneNumber = phoneNumberEt.text.toString()
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()
            val name = nameEt.text.toString()
            registerViewModel.registerUser(this, User("",name,email,phoneNumber,password, "user"), confPasswordEt.text.toString())

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
                Log.d("GuestRegisterActivity", "Timeleft" + timeLeft.toString())
                updateTimer()
            }

            override fun onFinish() {
                timerRunning = false
                updateTimer()
            }
        }
        countDownTimer.start()
    }


    private fun updateUIRunnable(text: String): Runnable {
        return Runnable {
            counterText.text = text
        }
    }
    override fun resetTimer() {
        timeLeft = 60000
        updateTimer()
    }

    override fun updateTimer() {
        var seconds:Int = (timeLeft / 1000).toInt()
        Log.d("GuestRegisterActivity", "seconds " + seconds.toString())
        var str = String.format("Resend code in %d", seconds)
        handler.post(updateUIRunnable(str))
    }
}