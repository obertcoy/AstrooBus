package com.sroo.astroobus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.sroo.astroobus.interfaces.IBackable

class GuestRegisterActivity : AppCompatActivity(), IBackable {

    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confPasswordEt: EditText
    private lateinit var phoneNumberEt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        nameEt = findViewById(R.id.register_et_name)
        emailEt = findViewById(R.id.register_et_email)
        passwordEt = findViewById(R.id.register_et_password)
        confPasswordEt = findViewById(R.id.register_et_conf_password)
        phoneNumberEt = findViewById(R.id.register_et_phone)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_register)

        back(findViewById(R.id.register_back_arrow))
    }

    override fun back(backBtn: ImageView) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }
}