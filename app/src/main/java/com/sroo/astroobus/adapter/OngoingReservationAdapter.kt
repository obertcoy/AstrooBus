package com.sroo.astroobus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.components.CardOngoingReservation
import com.sroo.astroobus.databinding.CardOngoingReservationBinding
import com.sroo.astroobus.databinding.CardTicketBusBinding
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.BusTransaction

class OngoingReservationAdapter(private val transactionList: ArrayList<BusTransaction>) :
    RecyclerView.Adapter<OngoingReservationAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardOngoingReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val fromTv: TextView = binding.ongoingReservationFromTv
        private val destinationTv: TextView = binding.ongoingReservationDestinationTv
        private val dateTv: TextView = binding.ongoingReservationDateTv
        private val timeTv: TextView = binding.ongoingReservationTimeTv
        private val seatTv: TextView = binding.ongoingReservationSeatTv

        fun bind(transaction: BusTransaction) {
            fromTv.text = transaction.startingPoint
            destinationTv.text = transaction.destinationPoint
            timeTv.text = transaction.timeString
            dateTv.text = transaction.dateString
            seatTv.text = transaction.availableSeats.toString()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OngoingReservationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardOngoingReservationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

}
