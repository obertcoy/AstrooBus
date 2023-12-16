package edu.bluejack23_1.AstrooBus.interfaces

import edu.bluejack23_1.AstrooBus.model.Bus

interface IClickable {
    fun onDeployClick(bus: Bus)
    fun onUpdateStatus()
}