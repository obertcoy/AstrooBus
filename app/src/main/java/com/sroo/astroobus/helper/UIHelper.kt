package com.sroo.astroobus.helper

import android.content.Context
import android.widget.Toast

class UIHelper() {

    fun createToast(context:Context, string: String){
        Toast.makeText(
            context,
            string,
            Toast.LENGTH_SHORT,
        ).show()
    }

}