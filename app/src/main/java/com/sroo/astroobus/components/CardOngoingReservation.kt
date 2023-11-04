package com.sroo.astroobus.components

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.sroo.astroobus.R
import com.sroo.astroobus.databinding.CardOngoingReservationBinding

class CardOngoingReservation @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs){

    private lateinit var binding: CardOngoingReservationBinding
    private lateinit var currActivity: Activity

    init {

        LayoutInflater.from(context).inflate(R.layout.card_ticket_bus, this, true)
        binding = CardOngoingReservationBinding.inflate(LayoutInflater.from(context), this, true)

        if (context is Activity) {
            currActivity = context
        }

    }
}