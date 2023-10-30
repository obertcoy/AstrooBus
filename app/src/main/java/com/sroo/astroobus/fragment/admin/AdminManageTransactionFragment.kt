package com.sroo.astroobus.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.adapter.BusTicketAdapter
import com.sroo.astroobus.adapter.ManageBusAdapter
import com.sroo.astroobus.databinding.FragmentAdminManageBusBinding
import com.sroo.astroobus.databinding.FragmentAdminManageTransactionBinding
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.`view-model`.BusTransactionViewModel

class AdminManageTransactionFragment: Fragment() {

    private lateinit var binding: FragmentAdminManageTransactionBinding
    private lateinit var allTransaction: ArrayList<BusTransaction>
    private var viewTransactionModel = BusTransactionViewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recylerViewAdapter: BusTicketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminManageTransactionBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    private fun initData() {
        viewTransactionModel.getAllBusTransaction {
            result->
            if(result != null){
                allTransaction = result
                recylerViewAdapter = BusTicketAdapter(allTransaction, null)
                recyclerView = binding.transactionBusRv
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = recylerViewAdapter
            }
        }
    }

}