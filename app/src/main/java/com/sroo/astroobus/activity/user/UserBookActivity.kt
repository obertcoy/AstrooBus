package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserBookBinding
import com.sroo.astroobus.utils.SessionManager

class UserBookActivity: AppCompatActivity() {

    private lateinit var binding : ActivityUserBookBinding
    private lateinit var intent: Intent
    private lateinit var curr_uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()
        binding = ActivityUserBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrUser()
        initData()
        next(binding.bookBackHomeBtn)
    }

    private fun getCurrUser(){
        intent = getIntent()
        if(SessionManager(this).getCurrUser().equals("")){
            curr_uid = intent.getStringExtra("CURR_UID").toString()
        }else{
            curr_uid = SessionManager(this).getCurrUser().toString()
        }
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
            userMainIntent.putExtra("CURR_UID",curr_uid)
            startActivity(userMainIntent)
        }
    }

}