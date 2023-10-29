package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserBusBinding
import com.sroo.astroobus.helper.AdapterHelper
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.interfaces.INavigable
import com.sroo.astroobus.model.Bus
import com.sroo.astroobus.utils.SessionManager
import com.sroo.astroobus.`view-model`.BusTransactionViewModel
import com.sroo.astroobus.`view-model`.BusViewModel
import com.sroo.astroobus.`view-model`.UserTransactionViewModel
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener

class UserBusActivity: AppCompatActivity(), INavigable, SeatClickListener, Bus.BusUpdateListener {

    private lateinit var binding : ActivityUserBusBinding
    private lateinit var intent: Intent
    private lateinit var busId: String
    private lateinit var transactionId: String
    private lateinit var seats: String
    private lateinit var seatsText: TextView
    private lateinit var seatBookView: SeatBookView
    private lateinit var seatString: String
    private var adapter = AdapterHelper()
    private var busViewModel =  BusViewModel()
    private var busTransactionViewModel = BusTransactionViewModel()
    private var viewModel = UserTransactionViewModel()
    private var price = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()
        binding = ActivityUserBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        back(binding.busBackArrow)
        next(binding.busSubmitBtn)
    }

    private fun initData(){
        binding.busFromTv.text = intent.getStringExtra("STARTING_POINT")
        binding.busDestinationTv.text = intent.getStringExtra("DESTINATION_POINT")
        binding.busDateTv.text = intent.getStringExtra("DATE")
        binding.busTimeTv.text = intent.getStringExtra("TIME")
        binding.busPriceTv.text = intent.getStringExtra("PRICE")
        price = intent.getStringExtra("PRICE")?.toInt() ?: 0
        busId = intent.getStringExtra("BUS_ID").toString()
        transactionId = intent.getStringExtra("TRANSACTION_ID").toString()
        Log.d("UserBusActivity", "Ini Init")
        seatsText = binding.busSeatSelectedTv
        seatsText.text = "0"
        busViewModel.getBus(busId, transactionId){
            result->
            if(result != null){
                seats = result.seatString
                seatString = seats
                Log.d("UserBusActivity", result.seatString)

                seatBookView = binding.layoutSeat
                seatBookView.setSeatsLayoutString(seats)
                    .setSeatLayoutPadding(2)
                    .setSeatSizeBySeatsColumnAndLayoutWidth(5, -1)


                seatBookView.show()
                result.setUpdateListener(this@UserBusActivity)
                seatBookView.setSeatClickListener(this)
            }
        }

    }

    override fun next(nextBtn: View) {

        nextBtn.setOnClickListener{

            if(seatBookView.getSelectedIdList().size > 0){
                val bookIntent = Intent(this, UserBookActivity::class.java)
                val seats =  adapter.arrayListToString(seatBookView.getSelectedIdList())
                val seatArr = seatBookView.getSelectedIdList()
                val total = seatBookView.getSelectedIdList().size * price
                bookIntent.putExtra("STARTING_POINT", binding.busFromTv.text)
                bookIntent.putExtra("DESTINATION_POINT", binding.busDestinationTv.text)
                bookIntent.putExtra("DATE", binding.busDateTv.text)
                bookIntent.putExtra("TIME", binding.busTimeTv.text)
                bookIntent.putExtra("PRICE", total.toString())
                bookIntent.putExtra("SEAT_TOTAL", binding.busSeatSelectedTv.text)
                bookIntent.putExtra("SEAT",seats)
                val sessionManager = SessionManager(this)
                val currUser = sessionManager.getCurrUser()
                if(currUser != null){
                    viewModel.addUserTransaction(transactionId,seats,total, currUser)
                    busViewModel.updateBusSeats(seatString,seatArr,busId)
                    busTransactionViewModel.updateAvailableSeatNum(transactionId, seatArr.size)

                }

                this.startActivity(bookIntent)
            }else{
                UIHelper.createToast(this, "You must select a seat")
            }

        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }

    override fun onAvailableSeatClick(selectedIdList: List<Int>, view: View) {
//        UIHelper.createToast(this , "LIST" + selectedIdList.toString())
        seatsText.text = seatBookView.getSelectedIdList().size.toString()
    }

    override fun onBookedSeatClick(view: View) {
        UIHelper.createToast(this, "This seat is already taken")
    }

    override fun onReservedSeatClick(view: View) {
        UIHelper.createToast(this, "This seat is already taken")
    }

    override fun onUpdate(seatString: String) {
        seats = seatString
//        updateSeatLayout()
    }

//    private fun updateSeatLayout() {
//        seatBookView.removeAllViews()
//
//        // Update your SeatBookView with the new seat layout
//        seatBookView.setSeatsLayoutString(seats)
//            .setSeatLayoutPadding(2)
//            .setSeatSizeBySeatsColumnAndLayoutWidth(5, -1)
//
//        seatBookView.show()
//    }
}