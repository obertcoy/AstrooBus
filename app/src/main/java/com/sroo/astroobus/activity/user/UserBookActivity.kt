package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserBookBinding
import com.sroo.astroobus.databinding.ActivityUserTicketBinding
import com.sroo.astroobus.interfaces.INavigable

class UserBookActivity: AppCompatActivity() {

    private lateinit var binding : ActivityUserBookBinding
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()
        binding = ActivityUserBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        next(binding.bookBackHomeBtn)
    }

    private fun initData(){

        binding.bookLocationTv.text = intent.getStringExtra("STARTING_POINT") + " - " + intent.getStringExtra("DESTINATION_POINT")
        binding.bookTimeTv.text = intent.getStringExtra("TIME")
        binding.bookSeatTv.text = intent.getStringExtra("SEAT")
        binding.bookPriceTv.text = intent.getStringExtra("PRICE")
    }

    private fun next(btn: View){
        btn.setOnClickListener{
            val userMainIntent = Intent(this, UserMainActivity::class.java)
            startActivity(userMainIntent)
        }
    }

}