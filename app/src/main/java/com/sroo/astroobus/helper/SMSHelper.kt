package com.sroo.astroobus.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat

class SMSHelper {
    fun sendSMS(code: String, phoneNum: String) {
        val smsManager = SmsManager.getDefault()
        val message = "This is your verification code $code for AstrooBus"

        smsManager.sendTextMessage(phoneNum, null, message, null, null)
    }

    fun sendSMSWithPermission(activity: Activity, code: String, phoneNum: String){
        if(ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.SEND_SMS), 131)
        }else{
            sendSMS(code, phoneNum)
        }
    }

}