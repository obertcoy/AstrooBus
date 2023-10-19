package com.sroo.astroobus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.databinding.CardManageBusBinding
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction

class ManageBusAdapter (private var busList: ArrayList<Bus>):
    RecyclerView.Adapter<ManageBusAdapter.ViewHolder>()
{
    class ViewHolder(binding: CardManageBusBinding):
        RecyclerView.ViewHolder(binding.root){

        private val idTv: TextView = binding.manageBusIdTv
        private val plateTv: TextView = binding.manageBusPlateTv
        private val seatTv: TextView = binding.manageBusSeatTv
        private val statusTv: TextView = binding.manageBusStatusTv

        fun bind(bus: Bus) {
            idTv.text = bus.busId
            plateTv.text = bus.busPlate
            seatTv.text = bus.busSeats.toString()
            statusTv.text = bus.busStatus
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardManageBusBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return busList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(busList[position])
    }

    public fun updateData(newBusList: ArrayList<Bus>) {
        busList = newBusList
        notifyDataSetChanged()
    }

}