package com.sroo.astroobus.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sroo.astroobus.databinding.FragmentAdminManageBusBinding
import com.sroo.astroobus.databinding.FragmentAdminManageTransactionBinding

class AdminManageTransactionFragment: Fragment() {

    private lateinit var binding: FragmentAdminManageTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminManageTransactionBinding.inflate(inflater, container, false)


        return binding.root
    }

}