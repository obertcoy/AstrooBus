package com.sroo.astroobus.helper

import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.model.User
import java.io.Serializable
import java.text.NumberFormat
import java.util.Locale

class AdapterHelper() {

    fun arrayListToString(list: List<Int>): String {
        return list.joinToString(",")
    }
    fun UserToHashmap(user: User): HashMap<String, String?> {
        val userMap = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "phoneNum" to user.phoneNum,
            "uid" to user.uid,
            "password" to user.password,
            "role" to user.role
        )

        return userMap
    }

    fun busTransactionToHashmap(busTransaction: BusTransaction): HashMap<String, Any>{
        val busTransactionMap = hashMapOf<String, Any>(
            "availableSeats" to 20,
            "busId" to busTransaction.busId,
            "dateString" to busTransaction.dateString,
            "destinationPoint" to busTransaction.destinationPoint,
            "price" to busTransaction.price,
            "startingPoint" to busTransaction.startingPoint,
            "time" to busTransaction.time,
            "timeString" to busTransaction.timeString
        )

        return busTransactionMap
    }

    fun phoneHashMap(phoneNum: String, code: String): HashMap<String, String> {
        val phoneMap = hashMapOf(
            "phoneNum" to phoneNum,
            "code" to code,
            "status" to "unverified"
        )

        return phoneMap
    }

    fun userTransactionHashMap(transactionId:String, seatsNumber:String,totalPrice:Number, userId:String): HashMap<String, Serializable> {
        val userTransactionHashMap = hashMapOf(
            "transactionId" to transactionId,
            "seatsNumber" to seatsNumber,
            "totalPrice" to totalPrice,
            "userId" to userId
        )
        return userTransactionHashMap
    }

    fun busToHashMap(bus: Bus): HashMap<String, Any> {
        val busHashMap = hashMapOf<String, Any>(
            "busPlateNumber" to bus.busPlate,
            "busStatus" to "Available",
            "seatString" to "/_____/AA_AA/AA_AA/AA_AA/AA_AA/AA_AA/AA_AA/AA_AA/AA_AA",
            "seats" to 20,
            "transactionId" to ""
        )
        return busHashMap
    }

    companion object {
        fun convertToRupiah(amount: Int): String {
            val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            return format.format(amount)
        }
    }

}