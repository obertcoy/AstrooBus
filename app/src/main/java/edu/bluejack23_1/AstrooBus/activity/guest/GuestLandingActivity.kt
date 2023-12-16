package edu.bluejack23_1.AstrooBus.activity.guest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.bluejack23_1.AstrooBus.R
import edu.bluejack23_1.AstrooBus.activity.user.UserMainActivity
import edu.bluejack23_1.AstrooBus.utils.SessionManager

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
            val adminMainIntent = Intent(this, edu.bluejack23_1.AstrooBus.activity.admin.AdminMainActivity::class.java)
            if(sessionManager.getCurrRole() == "user"){
                userMainIntent.putExtra("CURR_UID",sessionManager.getCurrUser() )
                startActivity(userMainIntent)
            }else{
                adminMainIntent.putExtra("CURR_UID",sessionManager.getCurrUser())
                startActivity(adminMainIntent)
            }


        }
    }
}