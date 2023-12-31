package com.sroo.astroobus.interfaces

import android.view.View
import androidx.fragment.app.Fragment

interface IFragmentable {
    fun navigate()
    fun changeFragment(fragment: Fragment, indicator: View)
}