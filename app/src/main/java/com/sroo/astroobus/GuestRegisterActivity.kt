package com.sroo.astroobus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.sroo.astroobus.interfaces.IBackable

class GuestRegisterActivity : AppCompatActivity(), IBackable {
    override fun onCreate(savedInstanceState: Bundle?) {
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