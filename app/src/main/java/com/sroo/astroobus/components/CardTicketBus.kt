package com.sroo.astroobus.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.sroo.astroobus.R
import com.sroo.astroobus.databinding.CardTicketBusBinding

class CardTicketBus @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private lateinit var binding: CardTicketBusBinding

    init {
        LayoutInflater.from(context).inflate(R.layout.card_ticket_bus, this, true)
        binding = CardTicketBusBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun toSeatPage(btn: View){

        btn.setOnClickListener{

        }
    }

}