package com.sroo.astroobus.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.user.UserBusActivity
import com.sroo.astroobus.databinding.CardTicketBusBinding
import com.sroo.astroobus.model.BusTransaction

class CardTicketBus @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs){

    private lateinit var binding: CardTicketBusBinding
    private lateinit var currActivity: Activity

    init {

        LayoutInflater.from(context).inflate(R.layout.card_ticket_bus, this, true)
        binding = CardTicketBusBinding.inflate(LayoutInflater.from(context), this, true)

        if (context is Activity) {
            currActivity = context
            toBusPage(binding.ticketBusCardBtn)
        }

    }

    private fun toBusPage(btn: View){

        btn.setOnClickListener{

            val busIntent = Intent(currActivity, UserBusActivity::class.java)

            busIntent.putExtra("STARTING_POINT", currActivity.intent.getStringExtra("STARTING_POINT"))
            busIntent.putExtra("DESTINATION_POINT", currActivity.intent.getStringExtra("DESTINATION_POINT"))
            busIntent.putExtra("DATE", currActivity.intent.getStringExtra("DATE"))
            busIntent.putExtra("TIME", binding.ticketBusCardTimeTv.text)
            busIntent.putExtra("PRICE", binding.ticketBusCardPriceTv.text)

            currActivity.startActivity(busIntent)
        }
    }



}