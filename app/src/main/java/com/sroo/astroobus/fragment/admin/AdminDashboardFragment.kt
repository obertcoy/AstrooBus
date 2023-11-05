package com.sroo.astroobus.fragment.admin

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sroo.astroobus.databinding.FragmentAdminDashboardBinding
import com.sroo.astroobus.databinding.FragmentUserHomeBinding
import com.sroo.astroobus.helper.TimeHelper
import com.sroo.astroobus.repository.BusRepository
import com.sroo.astroobus.`view-model`.BusTransactionViewModel
import com.sroo.astroobus.`view-model`.BusViewModel
import java.sql.Time

class AdminDashboardFragment: Fragment() {

    private lateinit var binding: FragmentAdminDashboardBinding
    private lateinit var currActivity: Activity
    private lateinit var dateTv:TextView
    private lateinit var timeTv:TextView
    private lateinit var activeTv:TextView
    private lateinit var nonActiveTv:TextView
    private lateinit var todayTransTv: TextView
    private var activeBus = 0
    private var nonActiveBus = 0
    private var todayTransaction = 0

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable: Runnable = object : Runnable {
        override fun run() {
            refreshDateTime()
            handler.postDelayed(this, 60000) // 60 seconds
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        currActivity = requireActivity()

        initData()
        activeTv = binding.adminDashboardActiveBusTv
        nonActiveTv = binding.adminDashboardNonactiveBusTv
        todayTransTv = binding.adminDashboardReservationsTodayTv
        BusViewModel().getAllBus {
            result->
            if(result != null){
                val size = result.size
                activeBus = result.filter { it.busStatus == "Available" }.size
                activeTv.text = activeBus.toString()
                nonActiveBus = (size- activeBus as Int)
                nonActiveTv.text = nonActiveBus.toString()
            }
        }
        BusTransactionViewModel().getAllTodayTransaction(dateTv.text.toString()){
            result ->
            if(result != null){
                var size = result.size
                var curr = 0
                var transactions = 0
                for(trans in result){
                    transactions += 20 - trans.availableSeats.toInt()
                    curr++;
                }
                if(curr == size){
                    todayTransaction = transactions
                    todayTransTv.text = todayTransaction.toString()
                }
            }
        }

        return binding.root
    }



    fun initData(){
        dateTv = binding.adminDashboardDateTv
        timeTv = binding.adminDashboardTimeTv

        dateTv.text = TimeHelper.timestampToDate(System.currentTimeMillis())
        timeTv.text = TimeHelper.timestampToTime(System.currentTimeMillis())
    }

    private fun refreshDateTime() {
        dateTv.text = TimeHelper.timestampToDate(System.currentTimeMillis())
        timeTv.text = TimeHelper.timestampToTime(System.currentTimeMillis())
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable)
    }

    override fun onResume() {
        super.onResume()
        handler.post(updateRunnable)
        activeTv.text = activeBus.toString()
        nonActiveTv.text = nonActiveBus.toString()
        todayTransTv.text = todayTransaction.toString()
    }
}