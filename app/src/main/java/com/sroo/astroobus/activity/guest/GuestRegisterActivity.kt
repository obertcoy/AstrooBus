package com.sroo.astroobus.activity.guest

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.R

class GuestRegisterActivity : AppCompatActivity(), INavigable {

    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_register)

        back(findViewById(R.id.register_back_arrow))
        verifyRegister(findViewById(R.id.register_btn_register))
    }

    private fun verifyRegister(registerBtn: Button){

        dialog = Dialog(this)
        registerBtn.setOnClickListener{
            showVerificationDialog("Check your messages")
        }
    }

    private fun showVerificationDialog(infoText: String) {

        dialog.setContentView(R.layout.dialog_verification)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.setCancelable(false)

        val backBtn = dialog.findViewById<ImageView>(R.id.verification_back_arrow)
        val infoTv = dialog.findViewById<TextView>(R.id.verification_tv_info)
        val submitBtn = dialog.findViewById<Button>(R.id.verification_submit)

        infoTv.text = infoText
//        backBtn.setOnClickListener{ dialog.dismiss() }
        backBtn.visibility = View.GONE

        submitBtn.setOnClickListener{
            next(submitBtn)
        }

        dialog.show()
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