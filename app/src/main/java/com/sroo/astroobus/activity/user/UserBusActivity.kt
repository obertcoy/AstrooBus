package com.sroo.astroobus.activity.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sroo.astroobus.databinding.ActivityUserBusBinding
import com.sroo.astroobus.databinding.ActivityUserTicketBinding
import dev.jahidhasanco.seatbookview.SeatBookView

class UserBusActivity: AppCompatActivity() {
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
        initData()
    }

    private fun initData(){

    }
}