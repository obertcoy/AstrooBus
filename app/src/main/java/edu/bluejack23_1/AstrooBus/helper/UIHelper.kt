package edu.bluejack23_1.AstrooBus.helper

import android.content.Context
import android.widget.Toast

class UIHelper() {

    companion object {
        fun createToast(context: Context, string: String) {
            Toast.makeText(
                context,
                string,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }


}