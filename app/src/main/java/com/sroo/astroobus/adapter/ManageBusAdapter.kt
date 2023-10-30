package com.sroo.astroobus.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.sroo.astroobus.databinding.CardManageBusBinding
import com.sroo.astroobus.interfaces.IClickable
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.`view-model`.BusViewModel

class ManageBusAdapter (private var busList: ArrayList<Bus>, private var listener: IClickable):
    RecyclerView.Adapter<ManageBusAdapter.ViewHolder>()
{
    class ViewHolder(binding: CardManageBusBinding,private var clickListener: IClickable):
        RecyclerView.ViewHolder(binding.root){

        private val idTv: TextView = binding.manageBusIdTv
        private val plateTv: TextView = binding.manageBusPlateTv
        private val seatTv: TextView = binding.manageBusSeatTv
        private val statusTv: TextView = binding.manageBusStatusTv
        private val switch: SwitchMaterial = binding.manageBusSwitch
        private val viewModel = BusViewModel()
        private val deployButton: CardView = binding.manageBusBtn

        fun bind(bus: Bus) {
            idTv.text = bus.busId
            plateTv.text = bus.busPlate
            seatTv.text = bus.busSeats.toString()
            statusTv.text = bus.busStatus
            switch.isChecked = bus.busStatus == "Available"
            deployButton.setOnClickListener {
                clickListener.onDeployClick(bus)
            }
        }

        init {
            switch.setOnClickListener {
                if(switch.isChecked == false){
                    statusTv.text = "Unavailable"
                    viewModel.updateBusStatus(idTv.text as String, "Unavailable")
                }else{
                    statusTv.text = "Available"
                    viewModel.updateBusStatus(idTv.text as String, "Available")
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardManageBusBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
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