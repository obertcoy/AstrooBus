package edu.bluejack23_1.AstrooBus.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import edu.bluejack23_1.AstrooBus.R
import edu.bluejack23_1.AstrooBus.databinding.CardManageBusBinding
import edu.bluejack23_1.AstrooBus.interfaces.IClickable
import edu.bluejack23_1.AstrooBus.model.Bus
import edu.bluejack23_1.AstrooBus.`view-model`.BusViewModel

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
        private lateinit var status:String
        private val deployButton: CardView = binding.manageBusBtn

        fun bind(bus: Bus) {
            idTv.text = bus.busId
            plateTv.text = bus.busPlate
            seatTv.text = bus.busSeats.toString()
            statusTv.text = bus.busStatus
            switch.isChecked = bus.busStatus == "Available"

            if(switch.isChecked){
                statusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.default_green))
            } else {
                statusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            }

            deployButton.setOnClickListener {
                Log.d("ManageBusAdapter", "hihiha")
                clickListener.onDeployClick(bus)
            }
        }

        init {
            switch.setOnClickListener {
                if(!switch.isChecked){
                    status = "Unavailable"
                    statusTv.text = status
                    viewModel.updateBusStatus(idTv.text as String, "Unavailable")
                    statusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                    clickListener.onUpdateStatus()

                }else{
                    status = "Available"
                    statusTv.text = status
                    viewModel.updateBusStatus(idTv.text as String, "Available")
                    statusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.default_green))
                    clickListener.onUpdateStatus()
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