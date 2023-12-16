package edu.bluejack23_1.AstrooBus.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import edu.bluejack23_1.AstrooBus.R
import edu.bluejack23_1.AstrooBus.databinding.CardHistoryBusBinding

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
