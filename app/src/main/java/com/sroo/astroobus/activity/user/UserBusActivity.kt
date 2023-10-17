package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserBusBinding
import com.sroo.astroobus.helper.UIHelper
import com.sroo.astroobus.interfaces.INavigable
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener

class UserBusActivity: AppCompatActivity(), INavigable, SeatClickListener {

    private lateinit var binding : ActivityUserBusBinding
    private lateinit var intent: Intent

    private var seats = (
                    "/_____" +
                    "/AA_AA" +
                    "/UA_AR" +
                    "/AA_AA" +
                    "/RU_AA" +
                    "/AA_AR" +
                    "/AU_AA" +
                    "/AA_AA" +
                    "/AAAAA"
            )
    private lateinit var seatBookView: SeatBookView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = getIntent()
        binding = ActivityUserBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .setSeatLayoutPadding(2)
            .setSeatSizeBySeatsColumnAndLayoutWidth(5, -1)


        seatBookView.show()
        seatBookView.setSeatClickListener(this)
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
    }

    override fun next(nextBtn: View) {

        nextBtn.setOnClickListener{

            val bookIntent = Intent(this, UserBookActivity::class.java)

            bookIntent.putExtra("STARTING_POINT", binding.busFromTv.text)
            bookIntent.putExtra("DESTINATION_POINT", binding.busDestinationTv.text)
            bookIntent.putExtra("DATE", binding.busDateTv.text)
            bookIntent.putExtra("TIME", binding.busTimeTv.text)
            bookIntent.putExtra("PRICE", binding.busPriceTv.text)
            bookIntent.putExtra("SEAT", binding.busSeatSelectedTv.text)

            this.startActivity(bookIntent)
        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }

    override fun onAvailableSeatClick(selectedIdList: List<Int>, view: View) {
        UIHelper.createToast(this , "LIST" + selectedIdList.toString())
    }

    override fun onBookedSeatClick(view: View) {
        TODO("Not yet implemented")
    }

    override fun onReservedSeatClick(view: View) {
        TODO("Not yet implemented")
    }
}