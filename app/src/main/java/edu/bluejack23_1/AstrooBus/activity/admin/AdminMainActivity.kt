package edu.bluejack23_1.AstrooBus.activity.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import edu.bluejack23_1.AstrooBus.R
import edu.bluejack23_1.AstrooBus.activity.universal.AccountActivity
import edu.bluejack23_1.AstrooBus.databinding.ActivityAdminMainBinding
import edu.bluejack23_1.AstrooBus.fragment.admin.AdminDashboardFragment
import edu.bluejack23_1.AstrooBus.fragment.admin.AdminManageFragment
import edu.bluejack23_1.AstrooBus.interfaces.IFragmentable
import edu.bluejack23_1.AstrooBus.utils.SessionManager

class AdminMainActivity: AppCompatActivity(), IFragmentable {

    private lateinit var binding: ActivityAdminMainBinding
    lateinit var currIndicator: View
    private lateinit var curr_uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrUser()
        currIndicator = binding.adminNavDashboardIndicator
        currIndicator.visibility = View.VISIBLE

        changeFragment(AdminDashboardFragment(), binding.adminNavDashboardIndicator)
        navigate()
    }

    private fun getCurrUser(){
        intent = getIntent()
        if(SessionManager(this).getCurrUser().equals("")){
            curr_uid = intent.getStringExtra("CURR_UID").toString()
        }else{
            curr_uid = SessionManager(this).getCurrUser().toString()
        }
    }

    override fun navigate() {

        binding.adminNavDashboard.setOnClickListener {
            changeFragment(AdminDashboardFragment(), binding.adminNavDashboardIndicator)
        }

        binding.adminNavBus.setOnClickListener {
            changeFragment(AdminManageFragment(), binding.adminNavBusIndicator)
        }

        binding.adminMainAccountIcon.setOnClickListener{
            val accountIntent = Intent(this, AccountActivity::class.java)
            accountIntent.putExtra("CURR_UID",curr_uid)
            startActivity(accountIntent)
        }
    }


    override fun changeFragment(fragment: Fragment, indicator: View) {
        val args = Bundle()
        args.putString("CURR_UID", curr_uid)
        fragment.arguments = args

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_main_frame_layout, fragment)
        fragmentTransaction.commit()

        currIndicator?.visibility = View.INVISIBLE
        currIndicator = indicator
        currIndicator?.visibility = View.VISIBLE
    }


}