package edu.bluejack23_1.AstrooBus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.AstrooBus.databinding.CardInformationBusBinding

class InformationAdapter(private val informations: ArrayList<String>) :
    RecyclerView.Adapter<InformationAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardInformationBusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val infoTv: TextView = binding.informationBus

        fun bind(information: String) {
            infoTv.text = information
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardInformationBusBinding.inflate(inflater, parent, false)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        binding.root.layoutParams = layoutParams
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(informations[position])
    }

    override fun getItemCount(): Int {
        return informations.size
    }

}
