package com.sroo.astroobus.interfaces

import com.sroo.astroobus.model.Bus

interface IClickable {
    fun onDeployClick(bus: Bus)
    fun onUpdateStatus()
}