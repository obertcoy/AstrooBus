package edu.bluejack23_1.AstrooBus.helper

import java.security.SecureRandom

class VerificationCodeHelper() {
    val range = 9
    val length = 6
    fun generateCode(): String{
        val secureRandom = SecureRandom()
        var s = ""
        var i = 0
        while(i < length){
            val num = secureRandom.nextInt(range)
            if(num == 0 && i == 0){
                i = -1
                continue
            }
            s += num
            i++
        }
        return s
    }
}