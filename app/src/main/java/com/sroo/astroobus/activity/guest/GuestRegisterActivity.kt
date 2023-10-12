package com.sroo.astroobus.activity.guest

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.R
import com.sroo.astroobus.helper.SMSHelper
import com.sroo.astroobus.helper.VerificationCodeHelper
import com.sroo.astroobus.model.User
import com.sroo.astroobus.repository.RegisterRepository
import com.sroo.astroobus.`view-model`.RegisterViewModel

class GuestRegisterActivity : AppCompatActivity(), INavigable {

    private lateinit var dialog: Dialog
    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confPasswordEt: EditText
    private lateinit var phoneNumberEt: EditText
    private lateinit var registerBtn: Button
    private lateinit var registerViewModel: RegisterViewModel
    private val codeGenerator: VerificationCodeHelper = VerificationCodeHelper()
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

        infoTv.text = infoText
        backBtn.setOnClickListener{ dialog.dismiss() }
//        backBtn.visibility = View.GONE

        submitBtn.setOnClickListener{
//            next(submitBtn)
            val phoneNumber = phoneNumberEt.text.toString()
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()
            val name = nameEt.text.toString()
            val code = inputText.text.toString()
            registerViewModel.verifyCode(this,code, User("",name,email,phoneNumber,password) )

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
}