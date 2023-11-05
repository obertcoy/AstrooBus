package com.sroo.astroobus.fragment.user

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.google.android.material.snackbar.Snackbar
import com.sroo.astroobus.activity.user.UserBusActivity
import com.sroo.astroobus.activity.user.UserMainActivity
import com.sroo.astroobus.activity.user.UserTicketActivity
import com.sroo.astroobus.adapter.OngoingReservationAdapter
import com.sroo.astroobus.databinding.FragmentUserHomeBinding
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.interfaces.IDropdownable
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.utils.LocationUtils
import com.sroo.astroobus.`view-model`.BusTransactionViewModel
import com.sroo.astroobus.`view-model`.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [UserHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserHomeFragment : Fragment(), IDropdownable {

    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var fromSelect: AutoCompleteTextView
    private lateinit var destinationSelect: AutoCompleteTextView
    private lateinit var searchBtn: View
    private lateinit var currActivity: Activity
    private lateinit var curr_uid:String

    private lateinit var reservationPager: ViewPager2
    private lateinit var emptyReservation: View
    private lateinit var ongoingReservation: ArrayList<BusTransaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        initDropdownData()

        searchBtn.setOnClickListener {
            submit()
        }

        selectDate()
        getCurrUser()
        currActivity = requireActivity()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val viewModel = BusTransactionViewModel()
        viewModel.getUserOnGoingReservation(curr_uid){
            result->
            if(result != null){
                Log.d("UserHomeFragment",result.size.toString())
                ongoingReservation = result
                Log.d("UserHomeFragment",ongoingReservation.size.toString())
            }else{
                ongoingReservation = ArrayList<BusTransaction>()
            }
            ongoingReservation()
        }
    }

    private fun ongoingReservation(){

        reservationPager = binding.homeReservationPager
        emptyReservation = binding.homeEmptyReservation

        if (ongoingReservation.isNotEmpty()) {
            reservationPager.visibility = View.VISIBLE
            emptyReservation.visibility = View.GONE
        } else {
            reservationPager.visibility = View.GONE
            emptyReservation.visibility = View.VISIBLE
        }

        val adapter = OngoingReservationAdapter(ongoingReservation)
        reservationPager.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun selectDate() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)

                if (selectedCalendar.before(calendar)) {
                    UIHelper.createToast(requireContext(), "Please select a future date.")
                } else {
                    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US)
                    val formattedDate = dateFormat.format(selectedCalendar.time)
                    binding.homeMenuDateTv.text = formattedDate
                    binding.homeMenuDateIcon.visibility = View.GONE
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        binding.homeMenuDate.setOnClickListener {
            datePickerDialog.show()
        }
    }


    override fun initDropdownData() {
        fromSelect = binding.homeFromDropdown
        destinationSelect = binding.homeToDropdown
        searchBtn = binding.homeMenuSearch

        fromSelect.setAdapter(LocationUtils.getLocationsAdapter(requireContext()))
        destinationSelect.setAdapter(LocationUtils.getLocationsAdapter(requireContext()))
    }

    override fun submit() {

        val startingPoint = fromSelect.text.toString()
        val destinationPoint = destinationSelect.text.toString()
        val selectedDate = binding.homeMenuDateTv.text.toString()


        if (!(LocationUtils.checkLocation(startingPoint) && LocationUtils.checkLocation(
                destinationPoint
            ))
        ) {
            UIHelper.createToast(requireContext(), "Invalid location")
            return
        }

        if (selectedDate.isEmpty()) {
            UIHelper.createToast(requireContext(), "Select a date")
            return
        }

        if (startingPoint == "" || destinationPoint == "" || selectedDate == "Date") {
            UIHelper.createToast(requireContext(), "All Fields Must Not Be Empty")
            return
        }

        if (startingPoint == destinationPoint) {
            UIHelper.createToast(requireContext(), "Invalid route")
            return
        }

        val ticketIntent = Intent(requireContext(), UserTicketActivity::class.java)

        ticketIntent.putExtra("STARTING_POINT", startingPoint)
        ticketIntent.putExtra("DESTINATION_POINT", destinationPoint)
        ticketIntent.putExtra("DATE", selectedDate)
        ticketIntent.putExtra("CURR_UID",curr_uid)
        startActivity(ticketIntent)

    }

    private fun getCurrUser(){
        val receivedValue = arguments?.getString("CURR_UID")
        if (receivedValue != null) {
            curr_uid = receivedValue
        }

    }

}