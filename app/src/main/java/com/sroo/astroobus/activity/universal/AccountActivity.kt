package com.sroo.astroobus.activity.universal

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.activity.guest.GuestLandingActivity
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.guest.GuestLoginActivity
import com.sroo.astroobus.databinding.ActivityAccountBinding
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.utils.SessionManager

class AccountActivity : AppCompatActivity(), INavigable {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var dialog: Dialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)

        next(binding.accountChangePasswordTv)
        back(binding.accountBackArrow)
        checkDialog(binding.accountNameEdit)
        checkDialog(binding.accountEmailEdit)
        checkDialog(binding.accountPhoneEdit)
        checkDialog(binding.logoutBtn)
    }

    private fun checkDialog(btn: View) {
        btn.setOnClickListener {
            if (btn == binding.accountNameEdit) showDialog("Enter your name")
            if (btn == binding.accountEmailEdit) showDialog("Enter your email")
            if (btn == binding.accountPhoneEdit) showDialog("Enter your phone")
            if(btn == binding.logoutBtn)logout()
        }

    }

    private fun logout(){
        val sessionManager = SessionManager(this)
        sessionManager.logout()
        val intent = Intent(this, GuestLandingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


    private fun showDialog(infoText: String) {

        dialog.setContentView(R.layout.dialog_account_edit)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.setCancelable(true)

        dialog.findViewById<TextView>(R.id.account_edit_tv).text = infoText

        val editTv = dialog.findViewById<EditText>(R.id.account_edit_et)
        editTv.hint = infoText

        if (infoText == "Enter your name"){
            editTv.text = binding.accountNameTv.editableText
        }
        if (infoText == "Enter your email"){
            editTv.text = binding.accountEmailTv.editableText
        }
        if (infoText == "Enter your phone"){
            editTv.text = binding.accountPhoneTv.editableText
        }

        dialog.findViewById<Button>(R.id.account_edit_btn).setOnClickListener{

            changeCredential(infoText, editTv.text.toString())
        }

        dialog.findViewById<ImageView>(R.id.account_edit_back_arrow).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun changeCredential(infoText: String, newValue: String){

        if (infoText == "Enter your name"){
            // change name
        }
        if (infoText == "Enter your email"){
            // change password
        }
        if (infoText == "Enter your phone"){
            // change password
        }

        UIHelper.createToast(this, "Credential changed successfully!")
        dialog.dismiss()

    }


    override fun next(nextBtn: View) {
        nextBtn.setOnClickListener{
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener {
            this.finish()
        }
    }
}