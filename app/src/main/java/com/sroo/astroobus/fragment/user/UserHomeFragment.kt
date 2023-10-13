package com.sroo.astroobus.fragment.user

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.sroo.astroobus.activity.user.UserTicketActivity
import com.sroo.astroobus.databinding.FragmentUserHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [UserHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var fromSelect: AutoCompleteTextView
    private lateinit var destinationSelect: AutoCompleteTextView
    private lateinit var searchBtn: ImageView

    private lateinit var locations: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        initData()

        searchBtn.setOnClickListener{
            search()
        }

        selectDate()

        return binding.root
    }

    private fun initData(){

        locations = arrayListOf<String>("Bina Nusantara", "Citywalk Lippo", "Blok M")
        locations.sort()

        fromSelect = binding.homeFromDropdown
        destinationSelect = binding.homeToDropdown
        searchBtn = binding.homeMenuSearch

        fromSelect.setAdapter(getSuggestions())
        destinationSelect.setAdapter(getSuggestions())
    }

    private fun search(){

        val fromLocation = fromSelect.text.toString()
        val destinationLocation = destinationSelect.text.toString()
        val selectedDate = binding.homeMenuDateTv.text.toString()

//        if(!(checkLocation(fromLocation) && checkLocation(destinationLocation))){
//            Snackbar.make(binding.root, "Invalid location", Snackbar.LENGTH_LONG).show()
//            return
//        }
//
//        if(selectedDate.isEmpty()){
//            Snackbar.make(binding.root, "Select a date", Snackbar.LENGTH_LONG).show()
//            return
//        }

        val ticketIntent = Intent(requireContext(), UserTicketActivity::class.java)

        ticketIntent.putExtra("FROM_LOCATION", fromLocation)
        ticketIntent.putExtra("DESTINATION_LOCATION", destinationLocation)
        ticketIntent.putExtra("SELECTED_DATE", selectedDate)

        startActivity(ticketIntent)
    }

    private fun selectDate(){

        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{_, year, month, dayOfMonth ->
            val dateFormat = SimpleDateFormat("EEE dd MMM yyyy", Locale.US)
            calendar.set(year, month, dayOfMonth)
            val formattedDate = dateFormat.format(calendar.time)
            binding.homeMenuDateTv.text = formattedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        binding.homeMenuDate.setOnClickListener{
            datePickerDialog.show()
        }
    }

    private fun getSuggestions(): ArrayAdapter<String>{
        return ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, locations)
    }

    private fun checkLocation(location: String): Boolean{
        return locations.contains(location)
    }






}