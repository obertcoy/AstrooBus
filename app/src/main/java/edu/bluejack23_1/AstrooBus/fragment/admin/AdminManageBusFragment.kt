package edu.bluejack23_1.AstrooBus.fragment.admin

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import edu.bluejack23_1.AstrooBus.R
import edu.bluejack23_1.AstrooBus.adapter.ManageBusAdapter
import edu.bluejack23_1.AstrooBus.databinding.FragmentAdminManageBusBinding
import edu.bluejack23_1.AstrooBus.helper.TimeHelper
import edu.bluejack23_1.AstrooBus.helper.UIHelper
import edu.bluejack23_1.AstrooBus.interfaces.IClickable
import edu.bluejack23_1.AstrooBus.interfaces.INavigable
import edu.bluejack23_1.AstrooBus.model.Bus
import edu.bluejack23_1.AstrooBus.model.BusTransaction
import edu.bluejack23_1.AstrooBus.utils.LocationUtils
import edu.bluejack23_1.AstrooBus.`view-model`.BusTransactionViewModel
import edu.bluejack23_1.AstrooBus.`view-model`.BusViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AdminManageBusFragment : Fragment(), INavigable, IClickable{

    private lateinit var viewModel: BusViewModel
    private var viewTransactionModel = BusTransactionViewModel()

    private lateinit var binding: FragmentAdminManageBusBinding
    private lateinit var dialog: Dialog
    private lateinit var dialogDeploy: Dialog

    private lateinit var allBuses: ArrayList<Bus>
    private lateinit var startingPoint: AutoCompleteTextView
    private lateinit var destinationPoint: AutoCompleteTextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var recylerViewAdapter: ManageBusAdapter
    private lateinit var currentFilter:String
    private lateinit var currentTime:String



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
        dialogDeploy = Dialog(requireContext())
        initData()
        displayAddDialog(binding.manageBusAddBtn)

        return binding.root
    }

    private fun initData() {
        viewModel.getAllBus { result ->
            if (result != null) {
                allBuses = result
                Log.d("AdminManageBusFragment", "allBuses size: ${allBuses.size}")
                recylerViewAdapter = ManageBusAdapter(allBuses, this)
                recyclerView = binding.manageBusRv
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = recylerViewAdapter
            }
            filter(binding.manageBusSpinner)
        }
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
        currentFilter = "All"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parentView.getItemAtPosition(position).toString()

                if(selectedOption == "All") {
                     currentFilter = "All"
                     recylerViewAdapter.updateData(allBuses)
                } else if (selectedOption == "Available") {
                    currentFilter = "Available"
                    filterBusesByStatus("Available")
                } else if (selectedOption == "Unavailable") {
                    currentFilter = "Unavailable"
                    filterBusesByStatus("Unavailable")
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }

    }
    private fun filterBusesByStatus(status: String) {
        val filteredBuses = allBuses.filter { it.busStatus == status }
        recylerViewAdapter.updateData(filteredBuses as ArrayList<Bus>)
    }


    private fun displayAddDialog(btn: View) {
        dialog.setContentView(R.layout.dialog_add_bus)
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

        next(dialog.findViewById<View>(R.id.add_bus_btn))
        btn.setOnClickListener {
            dialog.show()
        }
    }



    override fun next(nextBtn: View) {
        nextBtn.setOnClickListener{
            val plateEt = dialog.findViewById<EditText>(R.id.add_bus_plate_et)
//            Log.d("AdminManageBusFragment", "wiwuuu")
            viewModel.addBus(Bus("1", plateEt.text.toString(), 20,"p", "a",""), requireContext()){
                result->
                if(result == "success"){
                    viewModel.getAllBus { result ->
                        if (result != null) {
                            allBuses.clear()
                            allBuses.addAll(result)
                            recylerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            dialog.dismiss()
        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            dialog.dismiss()
        }
    }

    private fun initSpinner(spinner: Spinner) {

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.deploy_bus_time,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter

        spinner.setSelection(adapter.getPosition("7:00"))
        currentTime = "7:00"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parentView.getItemAtPosition(position).toString()
                currentTime = selectedOption
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }

    }

    override fun onDeployClick(bus:Bus) {
        Log.d("AdminManageBusFragment", "hihiha")
        dialogDeploy.setContentView(R.layout.dialog_deploy_bus)
        dialogDeploy.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogDeploy.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialogDeploy.setCancelable(true)

        val calendar = Calendar.getInstance()
        val dateView = dialogDeploy.findViewById<View>(R.id.deploy_bus_date_icon)
        val dateTv = dialogDeploy.findViewById<TextView>(R.id.deploy_bus_date)
        val priceTv = dialogDeploy.findViewById<TextView>(R.id.deploy_bus_price)

        val datePickerDialog = DatePickerDialog(
            requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)

                if (selectedCalendar.before(calendar)) {
                    UIHelper.createToast(requireContext(), "Please select a future date.")
                } else {
                    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US)
                    val formattedDate = dateFormat.format(selectedCalendar.time)
                    dateTv.text  = formattedDate
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        dateView.setOnClickListener {
            datePickerDialog.show()
        }
        val backButton = dialogDeploy.findViewById<View>(R.id.deploy_bus_back_arrow)
        val submitButton = dialogDeploy.findViewById<View>(R.id.deploy_bus_btn)
        initSpinner(dialogDeploy.findViewById<Spinner>(R.id.deploy_bus_time_dropdown))
        startingPoint = dialogDeploy.findViewById<View>(R.id.deploy_bus_from_dropdown) as AutoCompleteTextView
        destinationPoint = dialogDeploy.findViewById<View>(R.id.deploy_bus_to_dropdown) as AutoCompleteTextView
        startingPoint.setAdapter(LocationUtils.getLocationsAdapter(requireContext()))
        destinationPoint.setAdapter(LocationUtils.getLocationsAdapter(requireContext()))
        dialogDeploy.show()

        if (backButton != null) {
            backButton.setOnClickListener {
                dialogDeploy.dismiss()
            }
        }
        if(submitButton != null){
            submitButton.setOnClickListener {
                val dateString = dateTv.text.toString()
                val timeString = currentTime
                val currTime =  TimeHelper.convertDateTimeToTimestamp(dateString,timeString)
                val timestamp = if (currTime != null) {
                    Timestamp(Date(currTime))
                } else {
                    null
                }

                val dest = destinationPoint.text.toString()
                val start = startingPoint.text.toString()
                val pricing = priceTv.text.toString().toInt()
                val busTransaction = timestamp?.let { it1 ->
                    BusTransaction("1", bus.busId,dest, start,dateString ,timeString, pricing,20,
                        it1
                    )
                }
                if (busTransaction != null) {
                    viewTransactionModel.deployBus(busTransaction, requireContext())
                }
                dialogDeploy.dismiss()
            }
        }
    }

    override fun onUpdateStatus() {
        Log.d("AdminManageBusFragment", "onUpdate")
        viewModel.getAllBus { result ->
            if (result != null) {
                allBuses.clear()
                allBuses.addAll(result)
                recylerViewAdapter.notifyDataSetChanged()
            }
            if(currentFilter.equals("Unavailable") || currentFilter.equals("Available")){
                Log.d("AdminManageBusFragment", "update bus")
                Log.d("AdminManageBusFragment", currentFilter)
                filterBusesByStatus(currentFilter)
            }
        }
    }

//    private fun selectDate(datePicker: ImageView) {
//        val calendar = Calendar.getInstance()
//
//        val datePickerDialog = DatePickerDialog(
//            requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//                val selectedCalendar = Calendar.getInstance()
//                selectedCalendar.set(year, month, dayOfMonth)
//
//                if (selectedCalendar.before(calendar)) {
//                    UIHelper.createToast(requireContext(), "Please select a future date.")
//                } else {
//                    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US)
//                    val formattedDate = dateFormat.format(selectedCalendar.time)
//                    binding.homeMenuDateTv.text = formattedDate
//                }
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//
//        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
//
//        binding.homeMenuDate.setOnClickListener {
//            datePickerDialog.show()
//        }
//    }

}