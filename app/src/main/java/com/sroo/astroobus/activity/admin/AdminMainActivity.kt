package com.sroo.astroobus.activity.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.universal.AccountActivity
import com.sroo.astroobus.databinding.ActivityAdminMainBinding
import com.sroo.astroobus.fragment.admin.AdminDashboardFragment
import com.sroo.astroobus.fragment.admin.AdminManageBusFragment
import com.sroo.astroobus.fragment.user.UserHistoryFragment
import com.sroo.astroobus.fragment.user.UserHomeFragment
import com.sroo.astroobus.interfaces.IFragmentable

class AdminMainActivity: AppCompatActivity(), IFragmentable {

    private lateinit var binding: ActivityAdminMainBinding
    lateinit var currIndicator: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currIndicator = binding.adminNavDashboardIndicator
        currIndicator.visibility = View.VISIBLE

        changeFragment(AdminDashboardFragment(), binding.adminNavDashboardIndicator)
        navigate()
    }

    override fun navigate() {

        binding.adminNavDashboard.setOnClickListener {
            changeFragment(AdminDashboardFragment(), binding.adminNavDashboardIndicator)
        }

        binding.adminNavBus.setOnClickListener {
            changeFragment(AdminManageBusFragment(), binding.adminNavBusIndicator)
        }

        binding.adminMainAccountIcon.setOnClickListener{
            val accountIntent = Intent(this, AccountActivity::class.java)
            startActivity(accountIntent)
        }
    }


    override fun changeFragment(fragment: Fragment, indicator: View) {

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_main_frame_layout, fragment)
        fragmentTransaction.commit()

        currIndicator?.visibility = View.INVISIBLE
        currIndicator = indicator
        currIndicator?.visibility = View.VISIBLE
    }


}