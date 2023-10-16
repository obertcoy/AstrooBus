package com.sroo.astroobus.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.sroo.astroobus.R
import com.sroo.astroobus.databinding.CardHistoryBusBinding

class CardHistoryBus @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private val binding: CardHistoryBusBinding = CardHistoryBusBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
        useCompatPadding = true
        radius = 40f
    }
}
