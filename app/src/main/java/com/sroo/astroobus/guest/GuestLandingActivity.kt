package com.sroo.astroobus.guest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.R

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
    }
}