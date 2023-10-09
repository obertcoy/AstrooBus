package com.sroo.astroobus

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.interfaces.IBackable

class GuestLoginActivity: AppCompatActivity(), IBackable {

    override fun onCreate(savedInstanceState: Bundle?) {
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