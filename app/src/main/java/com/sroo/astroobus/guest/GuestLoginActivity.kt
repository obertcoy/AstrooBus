package com.sroo.astroobus.guest

import android.app.Dialog
import android.media.Image
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.R
import com.sroo.astroobus.interfaces.IBackable

class GuestLoginActivity: AppCompatActivity(), IBackable {

    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_login)

        back(findViewById(R.id.login_back_arrow))
        forgotPassword(findViewById(R.id.login_forgot_password))
    }

    private fun forgotPassword(forgotPassword : TextView){

        dialog = Dialog(this)
        forgotPassword.setOnClickListener{ showChooseVerificationDialog()}
    }

    private fun showChooseVerificationDialog() {

        dialog.setContentView(R.layout.dialog_choose_verification)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.setCancelable(false)

        val backBtn = dialog.findViewById<ImageView>(R.id.choose_verification_back_arrow)
        val emailBtn = dialog.findViewById<Button>(R.id.choose_verification_btn_email)
        val smsBtn = dialog.findViewById<Button>(R.id.choose_verification_btn_sms)

        backBtn.setOnClickListener { dialog.dismiss() }
        emailBtn.setOnClickListener { showVerificationDialog("Check your email") }
        smsBtn.setOnClickListener { showVerificationDialog("Check your messages") }

        dialog.show()
    }
    private fun showVerificationDialog(infoText: String) {

        dialog.setContentView(R.layout.dialog_verification)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.setCancelable(false)

        val backBtn = dialog.findViewById<ImageView>(R.id.verification_back_arrow)
        val infoTv = dialog.findViewById<TextView>(R.id.verification_tv_info)

        infoTv.text = infoText
        backBtn.setOnClickListener { showChooseVerificationDialog() }

        dialog.show()
    }

    override fun back(backBtn: ImageView) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }
}