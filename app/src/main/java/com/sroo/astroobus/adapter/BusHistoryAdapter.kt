package com.sroo.astroobus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.databinding.CardHistoryBusBinding
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.UserBusTicket

class BusHistoryAdapter (private var historyList: ArrayList<UserBusTicket>):
    RecyclerView.Adapter<BusHistoryAdapter.ViewHolder>()
{
    class ViewHolder(binding: CardHistoryBusBinding):
        RecyclerView.ViewHolder(binding.root){

        private val dateTv: TextView = binding.historyBusCardDateTv
        private val locationTv: TextView = binding.historyBusCardLocationTv
        private val timeTv: TextView = binding.historyBusCardTimeTv
        private val seatTv: TextView = binding.historyBusCardSeatTv
        private val priceTv: TextView = binding.historyBusCardPriceTv

        fun bind(ticket: UserBusTicket) {
            dateTv.text = ticket.dateString
            locationTv.text = "${ticket.startingPoint} - ${ticket.destinationPoint}"
            timeTv.text = "${ticket.startTimeString} - ${ticket.endTimeString}"
            seatTv.text = ticket.seat.toString()
            priceTv.text = AdapterHelper.convertToRupiah(ticket.price)
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