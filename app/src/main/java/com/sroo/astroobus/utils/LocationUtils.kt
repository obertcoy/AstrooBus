package com.sroo.astroobus.utils

import android.R
import android.content.Context
import android.widget.ArrayAdapter

class LocationUtils {

    companion object{
         fun getLocations() : ArrayList<String>{
            val locations = arrayListOf<String>("Bina Nusantara", "Citywalk Lippo", "Blok M", "Taman Anggrek", "Grand Indonesia", "Bekasi Barat", "Bandung")
             locations.sort()
            return locations
        }

        fun getLocationsAdapter(ctx: Context): ArrayAdapter<String> {
            return ArrayAdapter(ctx, R.layout.simple_dropdown_item_1line, getLocations())
        }

        fun checkLocation(location: String): Boolean{
            return getLocations().contains(location)
        }
    }
}