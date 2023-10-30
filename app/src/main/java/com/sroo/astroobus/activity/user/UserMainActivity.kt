package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.universal.AccountActivity
import com.sroo.astroobus.databinding.ActivityUserMainBinding
import com.sroo.astroobus.fragment.user.UserHistoryFragment
import com.sroo.astroobus.fragment.user.UserHomeFragment
import com.sroo.astroobus.interfaces.IFragmentable
import com.sroo.astroobus.`view-model`.BusTransactionViewModel

class UserMainActivity: AppCompatActivity(), IFragmentable {

    private lateinit var binding: ActivityUserMainBinding
    private lateinit var currIndicator: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currIndicator = binding.userNavHomeIndicator
        currIndicator.visibility = View.VISIBLE

        changeFragment(UserHomeFragment(), binding.userNavHomeIndicator)
        navigate()
        BusTransactionViewModel().deactivatePastBusTransactions()
    }

    override fun navigate() {

        binding.userNavHome.setOnClickListener {
            changeFragment(UserHomeFragment(), binding.userNavHomeIndicator)
        }

        binding.userNavHistory.setOnClickListener {
            changeFragment(UserHistoryFragment(), binding.userNavHistoryIndicator)
        }

        binding.userMainAccountIcon.setOnClickListener{
            val accountIntent = Intent(this, AccountActivity::class.java)
            startActivity(accountIntent)
        }
    }


    override fun changeFragment(fragment: Fragment, indicator: View) {

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.user_main_frame_layout, fragment)
        fragmentTransaction.commit()

        currIndicator?.visibility = View.INVISIBLE
        currIndicator = indicator
        currIndicator?.visibility = View.VISIBLE
    }


}