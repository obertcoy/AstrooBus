package com.sroo.astroobus.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.R
import com.sroo.astroobus.adapter.BusTicketAdapter
import com.sroo.astroobus.adapter.ManageBusAdapter
import com.sroo.astroobus.databinding.FragmentAdminManageBusBinding
import com.sroo.astroobus.databinding.FragmentAdminManageTransactionBinding
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.`view-model`.BusTransactionViewModel

class AdminManageTransactionFragment: Fragment(), BusTransaction.BusTransactionUpdateListener{

    private lateinit var binding: FragmentAdminManageTransactionBinding
    private lateinit var allTransaction: ArrayList<BusTransaction>
    private var viewTransactionModel = BusTransactionViewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recylerViewAdapter: BusTicketAdapter
    private lateinit var filterStatus: String

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

    override fun onResume() {
        super.onResume()
        BusTransactionViewModel().deactivatePastBusTransactions()
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

                allTransaction.forEach {
                    transition -> transition.setUpdateListener(this)
                }
            }
            filter(binding.transactionBusSpinner)
            filterBusesByStatus("Available")
        }
    }

    private fun filterBusesByStatus(status: String) {
        val filteredBuses = when (status) {
            "Available" -> allTransaction.filter { it.availableSeats.toInt() > 0  }
            "Sold Out" -> allTransaction.filter { it.availableSeats.toInt() <= 0 }
            else -> allTransaction
        }
        recylerViewAdapter.updateData(ArrayList(filteredBuses))
    }


    private fun filter(spinner: Spinner) {

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_bus_filter,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter

        spinner.setSelection(adapter.getPosition("Available"))
        filterStatus = "Available"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parentView.getItemAtPosition(position).toString()

                if(selectedOption == "Available") {
                    filterStatus = "Available"
                    filterBusesByStatus("Available")
                } else if (selectedOption == "Sold Out") {
                    filterStatus = "Sold Out"
                    filterBusesByStatus("Sold Out")
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }

    }

    override fun onUpdate() {
        viewTransactionModel.getAllBusTransaction {
                result->
                if(result != null){
                    allTransaction.clear()
                    allTransaction.addAll(result)
                    recylerViewAdapter.notifyDataSetChanged()

                    if(filterStatus == "Available") {
                        filterBusesByStatus("Available")
                    } else if (filterStatus == "Sold Out") {
                        filterBusesByStatus("Sold Out")
                    }
                }
        }
    }

}