package com.sroo.astroobus.fragment.admin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sroo.astroobus.databinding.FragmentAdminDashboardBinding
import com.sroo.astroobus.databinding.FragmentUserHomeBinding

class AdminDashboardFragment: Fragment() {

    private lateinit var binding: FragmentAdminDashboardBinding
    private lateinit var currActivity: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        currActivity = requireActivity()

        return binding.root
    }
}