package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.adapter.BusTicketAdapter
import com.sroo.astroobus.databinding.ActivityUserTicketBinding
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.utils.SessionManager
import com.sroo.astroobus.`view-model`.BusTransactionViewModel
import com.sroo.astroobus.`view-model`.TicketViewModel
import java.util.ArrayList

class UserTicketActivity: AppCompatActivity(), INavigable {

    private lateinit var binding : ActivityUserTicketBinding
    private lateinit var intent: Intent
    private var ticketVM = TicketViewModel()
    private lateinit var ticketRv: RecyclerView
    private lateinit var ticketAdapter: BusTicketAdapter
    private lateinit var ticketList: ArrayList<BusTransaction>
    private lateinit var curr_uid:String
    private var sort = "DESC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()

        binding = ActivityUserTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ticketRv = binding.ticketRV
        getCurrUser()
        initData()
        BusTransactionViewModel().deactivatePastBusTransactions()
        back(binding.ticketBackArrow)
    }

    override fun onResume() {
        super.onResume()

    }

    private fun getCurrUser(){
        intent = getIntent()
        if(SessionManager(this).getCurrUser().equals("")){
            curr_uid = intent.getStringExtra("CURR_UID").toString()
        }else{
            curr_uid = SessionManager(this).getCurrUser().toString()
        }
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
                ticketList = result
                ticketList.sortByDescending { it.price.toDouble() }
                setUpRecycler(ticketList)
            }
            filtering(binding.sortPrice)
        }
    }

    fun filtering(view: View){

        val sortIcon = binding.sortPriceBtn

        view.setOnClickListener {
            if(sort.equals("DESC")){
                sort = "ASC"
                ticketList.sortBy { it.price.toDouble() }
                setUpRecycler(ticketList)
                sortIcon.rotation += 180f
            }else{
                sort = "DESC"
                ticketList.sortByDescending { it.price.toDouble() }
                setUpRecycler(ticketList)
                sortIcon.rotation -= 180f
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
            intent.putExtra("CURR_UID",curr_uid)
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