package edu.bluejack23_1.AstrooBus.activity.universal

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.bluejack23_1.AstrooBus.databinding.ActivityChangePasswordBinding
import edu.bluejack23_1.AstrooBus.interfaces.INavigable
import edu.bluejack23_1.AstrooBus.utils.SessionManager
import edu.bluejack23_1.AstrooBus.`view-model`.AccountViewModel

class ChangePasswordActivity: AppCompatActivity(), INavigable {

    private lateinit var binding: ActivityChangePasswordBinding
    private var viewmodel = AccountViewModel()
    private lateinit var curr_uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrUser()
        next(binding.changePassBtn)
        back(binding.changePasswordBackArrow)
    }

    private fun getCurrUser(){
        intent = getIntent()
        if(SessionManager(this).getCurrUser().equals("")){
            curr_uid = intent.getStringExtra("CURR_UID").toString()
        }else{
            curr_uid = SessionManager(this).getCurrUser().toString()
        }
    }

    override fun next(nextBtn: View) {
        nextBtn.setOnClickListener{
            val oldPassword = binding.changePasswordOld.text.toString()
            val newPassword = binding.changePasswordNew.text.toString()
            val confirmPassword = binding.changePasswordConfirm.text.toString()
            if (curr_uid != null) {
                Log.d("ChangePasswordActivity", "tesssss")
                viewmodel.updatePassword(newPassword, oldPassword, confirmPassword,curr_uid,this)
            }

//            val mainIntent = Intent(this, UserMainActivity::class.java)
//            startActivity(mainIntent)
        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener{
            this.finish()
        }
    }
}