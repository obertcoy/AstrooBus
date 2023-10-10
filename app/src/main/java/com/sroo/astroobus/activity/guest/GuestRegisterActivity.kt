package com.sroo.astroobus.activity.guest

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sroo.astroobus.interfaces.IBackable
import com.sroo.astroobus.R

class GuestRegisterActivity : AppCompatActivity(), IBackable {

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

        infoTv.text = infoText
//        backBtn.setOnClickListener{ dialog.dismiss() }
        backBtn.visibility = View.GONE

        dialog.show()
    }

    override fun back(backBtn: ImageView) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }
}