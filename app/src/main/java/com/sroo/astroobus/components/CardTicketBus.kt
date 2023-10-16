package com.sroo.astroobus.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.cardview.widget.CardView
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.guest.GuestRegisterActivity
import com.sroo.astroobus.activity.user.UserBusActivity
import com.sroo.astroobus.databinding.CardTicketBusBinding

class CardTicketBus @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private lateinit var binding: CardTicketBusBinding
    private lateinit var currActivity: Activity
    private lateinit var  button:CardView

    init {
        LayoutInflater.from(context).inflate(R.layout.card_ticket_bus, this, true)
        binding = CardTicketBusBinding.inflate(LayoutInflater.from(context), this, true)
        button = binding.ticketBusCardBtn
        if (context is Activity) {
            currActivity = context
            button.setOnClickListener{
                val registerIntent = Intent(currActivity, UserBusActivity::class.java)
                currActivity.startActivity(registerIntent)
            }
        }

    }

    private fun toSeatPage(btn: View){

        btn.setOnClickListener{
            val registerIntent = Intent(currActivity, UserBusActivity::class.java)
            currActivity.startActivity(registerIntent)
        }
    }

}