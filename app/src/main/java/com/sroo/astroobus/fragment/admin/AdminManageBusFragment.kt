package com.sroo.astroobus.fragment.admin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.user.UserTicketActivity
import com.sroo.astroobus.adapter.ManageBusAdapter
import com.sroo.astroobus.databinding.FragmentAdminManageBusBinding
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.interfaces.IDropdownable
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.utils.LocationUtils
import com.sroo.astroobus.`view-model`.BusViewModel


class AdminManageBusFragment : Fragment(), INavigable{

    private lateinit var viewModel: BusViewModel

    private lateinit var binding: FragmentAdminManageBusBinding
    private lateinit var dialog: Dialog

    private lateinit var allBuses: ArrayList<Bus>
    private lateinit var activeBuses: ArrayList<Bus>
    private lateinit var nonActiveBuses: ArrayList<Bus>

    private lateinit var recyclerView: RecyclerView
    private lateinit var recylerViewAdapter: ManageBusAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminManageBusBinding.inflate(inflater, container, false)
        viewModel = BusViewModel()
        dialog = Dialog(requireContext())

        initData()
        filter(binding.manageBusSpinner)
        displayAddDialog(binding.manageBusAddBtn)

        return binding.root
    }

    private fun initData() {

        recyclerView = binding.manageBusRv
//        recylerViewAdapter = ManageBusAdapter(allBuses)
//        recyclerView.adapter = recylerViewAdapter

        // masukin ke masing arraylist


    }

    private fun filter(spinner: Spinner) {

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.manage_bus_filter,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter

        spinner.setSelection(adapter.getPosition("All"))

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>,
//                selectedItemView: View,
//                position: Int,
//                id: Long
//            ) {
//                val selectedOption = parentView.getItemAtPosition(position).toString()
//
//                if(selectedOption == "All") {
//                    recylerViewAdapter.updateData(allBuses)
//                } else if (selectedOption == "Active") {
//                    recylerViewAdapter.updateData(activeBuses)
//                } else if (selectedOption == "Non-Active") {
//                    recylerViewAdapter.updateData(nonActiveBuses)
//
//                }
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//
//            }
//        }

    }

    private fun displayAddDialog(btn: View) {
        dialog.setContentView(R.layout.dialog_deploy_bus)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.setCancelable(true)

        val backButton = dialog.findViewById<View>(R.id.add_bus_back_arrow)

        if (backButton != null) {
            backButton.setOnClickListener {
                dialog.dismiss()
            }
        } else {

        }

        next(binding.manageBusAddBtn)
        dialog.show()
    }



    override fun next(nextBtn: View) {

        val plateEt = dialog.findViewById<EditText>(R.id.add_bus_plate_et)
        nextBtn.setOnClickListener{
            viewModel.addBus(plateEt.text.toString(), requireContext())
        }

    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            dialog.dismiss()
        }
    }


}