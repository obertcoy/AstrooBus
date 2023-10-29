package com.sroo.astroobus.activity.universal

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.activity.user.UserMainActivity
import com.sroo.astroobus.databinding.ActivityChangePasswordBinding
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.interfaces.INavigable

class ChangePasswordActivity: AppCompatActivity(), INavigable {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun changePassword(){

    }

    override fun next(nextBtn: View) {
        nextBtn.setOnClickListener{

            val oldPassword = binding.changePasswordOld.text
            val newPassword = binding.changePasswordNew.text
            val confirmPassword = binding.changePasswordConfirm.text

            // check old password dari firebase tolong sheryl hehe
            if(!newPassword.equals(confirmPassword)) return@setOnClickListener;

            // tinggal ubah aja password nya
            changePassword()

            UIHelper.createToast(this, "Password changed successfully!")

            val mainIntent = Intent(this, UserMainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }
}