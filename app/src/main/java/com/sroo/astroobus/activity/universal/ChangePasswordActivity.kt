package com.sroo.astroobus.activity.universal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.activity.user.UserMainActivity
import com.sroo.astroobus.databinding.ActivityChangePasswordBinding
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.utils.SessionManager
import com.sroo.astroobus.`view-model`.AccountViewModel

class ChangePasswordActivity: AppCompatActivity(), INavigable {

    private lateinit var binding: ActivityChangePasswordBinding
    private var viewmodel = AccountViewModel()
    private lateinit var curr_uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrUser()
        next(binding.changePassBtn)
        back(binding.changePasswordBackArrow)
    }

    private fun getCurrUser(){
        intent = getIntent()
        if(SessionManager(this).getCurrUser().equals("")){
            curr_uid = intent.getStringExtra("CURR_UID").toString()
        }else{
            curr_uid = SessionManager(this).getCurrUser().toString()
        }
    }

    override fun next(nextBtn: View) {
        nextBtn.setOnClickListener{
            val oldPassword = binding.changePasswordOld.text.toString()
            val newPassword = binding.changePasswordNew.text.toString()
            val confirmPassword = binding.changePasswordConfirm.text.toString()
            if (curr_uid != null) {
                Log.d("ChangePasswordActivity", "tesssss")
                viewmodel.updatePassword(newPassword, oldPassword, confirmPassword,curr_uid,this)
            }

//            val mainIntent = Intent(this, UserMainActivity::class.java)
//            startActivity(mainIntent)
        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }
}