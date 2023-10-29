package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.adapter.BusTicketAdapter
import com.sroo.astroobus.databinding.ActivityUserTicketBinding
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.`view-model`.TicketViewModel
import java.util.ArrayList

class UserTicketActivity: AppCompatActivity(), INavigable {

    private lateinit var binding : ActivityUserTicketBinding
    private lateinit var intent: Intent
    private var ticketVM = TicketViewModel()
    private lateinit var ticketRv: RecyclerView
    private lateinit var ticketAdapter: BusTicketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()

        binding = ActivityUserTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ticketRv = binding.ticketRV

        initData()

        back(binding.ticketBackArrow)
    }

    private fun initData(){

        binding.ticketFromTv.text = intent.getStringExtra("STARTING_POINT")
        binding.ticketDestinationTv.text = intent.getStringExtra("DESTINATION_POINT")
        binding.ticketDateTv.text = intent.getStringExtra("DATE")

        val startingPoint = intent.getStringExtra("STARTING_POINT")
        val destinationPoint = intent.getStringExtra("DESTINATION_POINT")
        val date = intent.getStringExtra("DATE")
        ticketVM.getAllBusByRoute(startingPoint!!,destinationPoint!!, date!!){
            result->
            if(result != null){
                Log.d("UserTicketActivity", result.toString())
                setUpRecycler(result)
            }
        }
    }

    fun setUpRecycler(busList: ArrayList<BusTransaction>){
        ticketAdapter = BusTicketAdapter(busList) { busTransaction ->
            val intent = Intent(this, UserBusActivity::class.java)
            intent.putExtra("STARTING_POINT", busTransaction.startingPoint)
            intent.putExtra("DESTINATION_POINT", busTransaction.destinationPoint)
            intent.putExtra("DATE", busTransaction.dateString)
            intent.putExtra("TIME", busTransaction.timeString)
            intent.putExtra("PRICE", busTransaction.price.toString())
            intent.putExtra("BUS_ID", busTransaction.busId)
            intent.putExtra("TRANSACTION_ID", busTransaction.transactionId)
            startActivity(intent)
        }
        ticketRv.layoutManager = LinearLayoutManager(this)
        ticketRv.adapter = ticketAdapter
    }


    override fun next(nextBtn: View) {
        TODO("Not yet implemented")
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }

}