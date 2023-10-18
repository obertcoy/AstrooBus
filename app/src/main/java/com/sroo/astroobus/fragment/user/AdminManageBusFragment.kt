package com.sroo.astroobus.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sroo.astroobus.databinding.FragmentAdminManageBusBinding

class AdminManageBusFragment: Fragment() {

    private lateinit var binding: FragmentAdminManageBusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminManageBusBinding.inflate(inflater, container, false)


        return binding.root
    }

}