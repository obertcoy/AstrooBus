package com.sroo.astroobus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.databinding.CardTicketBusBinding
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.BusTicket
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class BusTicketAdapter (private var ticketList: ArrayList<BusTicket>):
    RecyclerView.Adapter<BusTicketAdapter.ViewHolder>()
{
    class ViewHolder(binding: CardTicketBusBinding):
    RecyclerView.ViewHolder(binding.root){

        private val locationTv: TextView = binding.ticketBusCardLocationTv
        private val timeTv: TextView = binding.ticketBusCardTimeTv
        private val estimateTv: TextView = binding.ticketBusCardEstimateTv
        private val seatTv: TextView = binding.ticketBusCardSeatTv
        private val priceTv: TextView = binding.ticketBusCardPriceTv

        fun bind(ticket: BusTicket) {
            locationTv.text = "${ticket.fromLocation} - ${ticket.destinationLocation}"
            timeTv.text = ticket.startTime

            val start = LocalTime.parse(ticket.startTime)
            val end = LocalTime.parse(ticket.endTime)
            val hoursDiff = ChronoUnit.HOURS.between(start, end)
            val minutesDiff = ChronoUnit.MINUTES.between(start, end) % 60

            estimateTv.text = String.format("Estimated travel time %d hour(s)", hoursDiff)
            seatTv.text = ticket.seats.toString()
            priceTv.text = AdapterHelper.convertToRupiah(ticket.price)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardTicketBusBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ticketList[position])
    }

}