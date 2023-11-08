package com.sroo.astroobus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.databinding.CardTicketBusBinding
import com.sroo.astroobus.databinding.CardTransactionBusBinding
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class BusTransactionAdapter (
    private var transactionList: ArrayList<BusTransaction>
):
    RecyclerView.Adapter<BusTransactionAdapter.ViewHolder>()
{
    inner class ViewHolder(binding: CardTransactionBusBinding):
        RecyclerView.ViewHolder(binding.root){

        private val locationTv: TextView = binding.ticketBusCardLocationTv
        private val timeTv: TextView = binding.ticketBusCardTimeTv
        private val estimateTv: TextView = binding.ticketBusCardEstimateTv
        private val seatTv: TextView = binding.ticketBusCardSeatTv
        private val priceTv: TextView = binding.ticketBusCardPriceTv

        fun bind(transaction: BusTransaction) {
            locationTv.text = "${transaction.startingPoint} - ${transaction.destinationPoint}"
            timeTv.text = transaction.timeString
            estimateTv.text = String.format("Estimated travel time %d hour(s)", 3)
            seatTv.text = transaction.availableSeats.toString()
            priceTv.text = AdapterHelper.convertToRupiah(transaction.price.toInt())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardTransactionBusBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusTransactionAdapter.ViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    public fun updateData(newBusList: ArrayList<BusTransaction>) {
        transactionList = newBusList
        notifyDataSetChanged()
    }

}