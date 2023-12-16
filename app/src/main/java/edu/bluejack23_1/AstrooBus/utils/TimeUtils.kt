package edu.bluejack23_1.AstrooBus.utils

import android.R
import android.content.Context
import android.widget.ArrayAdapter

class TimeUtils {

    companion object{
        fun getTimeList() : ArrayList<String>{
            val timeList = arrayListOf<String>(
                "00:00", "01:00", "02:00", "03:00", "04:00",
                "05:00", "06:00", "07:00", "08:00", "09:00",
                "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00",
                "20:00", "21:00", "22:00", "23:00")
            timeList.sort()
            return timeList
        }

        fun getTimeListAdapter(ctx: Context): ArrayAdapter<String> {
            return ArrayAdapter(ctx, R.layout.simple_dropdown_item_1line, getTimeList())
        }

        fun checkTime(time: String): Boolean{
            return getTimeList().contains(time)
        }
    }
}