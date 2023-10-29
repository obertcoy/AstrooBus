package com.sroo.astroobus.helper

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
            "role" to "user"
        )

        return userMap
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

    companion object {
        fun convertToRupiah(amount: Int): String {
            val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            return format.format(amount)
        }
    }

}