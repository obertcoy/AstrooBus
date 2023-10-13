package com.sroo.astroobus.activity.user

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserTicketBinding
import com.sroo.astroobus.interfaces.INavigable

class UserTicketActivity: AppCompatActivity(), INavigable {

    private lateinit var binding : ActivityUserTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun next(nextBtn: View) {
        TODO("Not yet implemented")
    }

    override fun back(backBtn: View) {
        TODO("Not yet implemented")
    }

}