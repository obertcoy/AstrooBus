package com.sroo.astroobus

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.interfaces.IBackable

class GuestLoginActivity: AppCompatActivity(), IBackable {

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginButton : Button
    private lateinit var rememberMeToggle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        emailEt = findViewById(R.id.login_et_email)
        passwordEt = findViewById(R.id.login_et_password)
        rememberMeToggle = findViewById(R.id.material_switch)
        loginButton = findViewById(R.id.login_btn_login)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_login)

        back(findViewById(R.id.login_back_arrow))

    }

    override fun back(backBtn: ImageView) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }
}