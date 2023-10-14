package com.sroo.astroobus.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.guest.GuestLoginActivity
import com.sroo.astroobus.activity.guest.GuestRegisterActivity
import com.sroo.astroobus.activity.user.UserMainActivity
import com.sroo.astroobus.utils.SessionManager

class GuestLandingActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_landing)

        val loginBtn = findViewById<Button>(R.id.landing_btn_login)

        loginBtn.setOnClickListener {
            val loginIntent = Intent(this, GuestLoginActivity::class.java)
            startActivity(loginIntent)
        }
        val registerBtn = findViewById<Button>(R.id.landing_btn_register)

        registerBtn.setOnClickListener {
            val registerIntent = Intent(this, GuestRegisterActivity::class.java)
            startActivity(registerIntent)
        }

        val sessionManager = SessionManager(this)
        if(sessionManager.getCurrUser() != ""){
            val userMainIntent = Intent(this, UserMainActivity::class.java)
            startActivity(userMainIntent)
        }
    }
}