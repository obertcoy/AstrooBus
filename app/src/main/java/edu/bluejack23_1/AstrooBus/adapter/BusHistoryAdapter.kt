package edu.bluejack23_1.AstrooBus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.AstrooBus.databinding.CardHistoryBusBinding
import edu.bluejack23_1.AstrooBus.model.HistoryTransaction
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
            timeTv.text = "Departure: ${ticket.timeString}"
            seatTv.text = "Seat: ${ticket.seatsNumber}"
            priceTv.text = "Rp ${ticket.totalPrice}"
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