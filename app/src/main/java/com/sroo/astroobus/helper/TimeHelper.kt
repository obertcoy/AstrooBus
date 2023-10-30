package com.sroo.astroobus.helper

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class TimeHelper() {
    companion object {
        fun getCurrentTimestamp(): Timestamp {
            val currentDateTime = LocalDateTime.now()
            val formattedString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
            return Timestamp.valueOf(formattedString)
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