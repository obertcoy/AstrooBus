<<<<<<<< HEAD:app/src/main/java/com/sroo/astroobus/guest/GuestLandingActivity.kt
package com.sroo.astroobus.guest
========
package com.sroo.astroobus.Activity
>>>>>>>> 2dcfe37a9f69b99112c51ec30bb61b62472fceb0:app/src/main/java/com/sroo/astroobus/Activity/GuestLandingActivity.kt

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