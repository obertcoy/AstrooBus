package com.sroo.astroobus.`view-model`

import android.content.Context
import com.sroo.astroobus.components.CardManageBus
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.utils.LocationUtils


class DeployBusViewModel(private val view: CardManageBus)  {

    fun deployBus(startingPoint: String, destinationPoint: String, startTime: String, endTime: String, ctx: Context){
        if (!(LocationUtils.checkLocation(startingPoint) && LocationUtils.checkLocation(
                destinationPoint
            ))
        ) {
            UIHelper.createToast(ctx, "Invalid location")
            return
        }

        if (startingPoint == "" || destinationPoint == "" || startTime == "" || endTime == "") {
            UIHelper.createToast(ctx, "All Fields Must Not Be Empty")
            return
        }

        if (startingPoint == destinationPoint) {
            UIHelper.createToast(ctx, "Invalid route")
            return
        }

        if(startTime >= endTime){
            UIHelper.createToast(ctx, "Invalid time")
            return
        }
        
        // deploy bus

    }
}