package com.sroo.astroobus.helper

import com.sroo.astroobus.model.User

class AdapterHelper() {

    fun UserToHashmap(user: User): HashMap<String, String?>{
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

    fun phoneHashMap(phoneNum:String, code:String): HashMap<String, String> {
        val phoneMap = hashMapOf(
            "phoneNum" to phoneNum,
            "code" to code,
            "status" to "unverified"
        )

        return phoneMap
    }
}