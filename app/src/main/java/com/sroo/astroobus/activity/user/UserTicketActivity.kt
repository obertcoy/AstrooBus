package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserTicketBinding
import com.sroo.astroobus.interfaces.INavigable

class UserTicketActivity: AppCompatActivity(), INavigable {

    private lateinit var binding : ActivityUserTicketBinding
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()

        binding = ActivityUserTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        back(binding.ticketBackArrow)
    }

    private fun initData(){

        binding.ticketFromTv.text = intent.getStringExtra("FROM_LOCATION")
        binding.ticketDestinationTv.text = intent.getStringExtra("DESTINATION_LOCATION")
        binding.ticketDateTv.text = intent.getStringExtra("SELECTED_DATE")
    }


    override fun next(nextBtn: View) {
        TODO("Not yet implemented")
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }

}