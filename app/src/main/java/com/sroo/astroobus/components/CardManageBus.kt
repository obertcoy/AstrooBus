package com.sroo.astroobus.components

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.user.UserBusActivity
import com.sroo.astroobus.databinding.CardManageBusBinding
import com.sroo.astroobus.databinding.CardTicketBusBinding

class CardManageBus @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private lateinit var binding: CardManageBusBinding
    private lateinit var currActivity: Activity
    private lateinit var dialog: Dialog

    init {

        LayoutInflater.from(context).inflate(R.layout.card_manage_bus, this, true)
        binding = CardManageBusBinding.inflate(LayoutInflater.from(context), this, true)

        if (context is Activity) {
            currActivity = context
            dialog = Dialog(currActivity)
        }

        changeBusStatus(binding.manageBusSwitch)
        displayDeployDialog(binding.manageBusBtn)

    }

    private fun changeBusStatus(switch: SwitchMaterial){

        switch.setOnCheckedChangeListener { _, isChecked ->
            val statusTv = binding.manageBusStatusTv

            if (isChecked) {

                statusTv.text = "Available"
                statusTv.setTextColor(ContextCompat.getColor(context, R.color.default_green))
                // ubah di db

            } else {

                statusTv.text = "Unavailable"
                statusTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                // ubah di db


            }
        }
    }

    private fun displayDeployDialog(btn: View){

        btn.setOnClickListener{

            dialog.setContentView(R.layout.dialog_deploy_bus)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
            dialog.setCancelable(true)

            dialog.show()
        }
    }



}