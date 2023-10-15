package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserTicketBinding

class UserBusActivity: AppCompatActivity() {
    private lateinit var binding : ActivityUserTicketBinding
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()

        binding = ActivityUserTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
    }

    private fun initData(){

        binding.ticketFromTv.text = intent.getStringExtra("FROM_LOCATION")
        binding.ticketDestinationTv.text = intent.getStringExtra("DESTINATION_LOCATION")
        binding.ticketDateTv.text = intent.getStringExtra("SELECTED_DATE")
    }
}