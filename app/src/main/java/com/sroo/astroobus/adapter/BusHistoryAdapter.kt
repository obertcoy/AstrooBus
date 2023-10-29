package com.sroo.astroobus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.databinding.CardHistoryBusBinding
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.HistoryTransaction
import com.sroo.astroobus.model.UserBusTicket
import com.sroo.astroobus.model.UserTransaction
import java.util.ArrayList

class BusHistoryAdapter(private var historyList: ArrayList<HistoryTransaction>):
    RecyclerView.Adapter<BusHistoryAdapter.ViewHolder>()
{
    class ViewHolder(binding: CardHistoryBusBinding):
        RecyclerView.ViewHolder(binding.root){

        private val dateTv: TextView = binding.historyBusCardDateTv
        private val locationTv: TextView = binding.historyBusCardLocationTv
        private val timeTv: TextView = binding.historyBusCardTimeTv
        private val seatTv: TextView = binding.historyBusCardSeatTv
        private val priceTv: TextView = binding.historyBusCardPriceTv

        fun bind(ticket: HistoryTransaction) {
            dateTv.text = ticket.date
            locationTv.text = "${ticket.startingPoint} - ${ticket.endingPoint}"
            timeTv.text = "${ticket.timeString}"
            seatTv.text = ticket.seatsNumber.toString()
            priceTv.text = ticket.totalPrice
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardHistoryBusBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

}