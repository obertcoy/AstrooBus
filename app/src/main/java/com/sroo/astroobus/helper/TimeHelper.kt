package com.sroo.astroobus.helper

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class TimeHelper() {
    companion object {
        fun getCurrentTimestamp(): Timestamp {
            val currentInstant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            return Timestamp(currentInstant.toEpochMilli() / 1000, (currentInstant.toEpochMilli() % 1000).toInt() * 1000)
        }

        fun timestampToDate(timestamp: Long): String {
            val date = Date(timestamp)
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy")
            return formatter.format(date)
        }

        fun timestampToTime(timestamp: Long): String {
            val date = Date(timestamp)
            val formatter = SimpleDateFormat("HH:mm")
            return formatter.format(date)
        }

    }

}