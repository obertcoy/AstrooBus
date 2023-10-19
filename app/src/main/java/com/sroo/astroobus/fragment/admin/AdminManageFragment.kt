package com.sroo.astroobus.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sroo.astroobus.R
import com.sroo.astroobus.databinding.FragmentAdminManageBinding
import com.sroo.astroobus.interfaces.IFragmentable

class AdminManageFragment: Fragment(), IFragmentable {

    private lateinit var binding: FragmentAdminManageBinding
    private lateinit var currIndicator: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminManageBinding.inflate(inflater, container, false)

        changeFragment(AdminManageBusFragment(), binding.manageNavBusTv)
        navigate()

        return binding.root
    }

    override fun navigate() {

        binding.manageNavBus.setOnClickListener{
            changeFragment(AdminManageBusFragment(), binding.manageNavBus)
            binding.manageNavBusTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }

        binding.manageNavTransaction.setOnClickListener{
            changeFragment(AdminManageTransactionFragment(), binding.manageNavTransaction)
            binding.manageNavTransactionTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

    override fun changeFragment(fragment: Fragment, indicator: View) {

        val fragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_manage_frame_layout, fragment)
        fragmentTransaction.commit()

        currIndicator?.setBackgroundResource(R.color.white);
        currIndicator = indicator
        currIndicator?.setBackgroundResource(R.color.transparent);
    }


}