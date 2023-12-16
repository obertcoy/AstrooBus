package edu.bluejack23_1.AstrooBus.activity.universal

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.bluejack23_1.AstrooBus.activity.guest.GuestLandingActivity
import edu.bluejack23_1.AstrooBus.R
import edu.bluejack23_1.AstrooBus.databinding.ActivityAccountBinding
import edu.bluejack23_1.AstrooBus.helper.UIHelper
import edu.bluejack23_1.AstrooBus.interfaces.INavigable
import edu.bluejack23_1.AstrooBus.model.User
import edu.bluejack23_1.AstrooBus.utils.SessionManager
import edu.bluejack23_1.AstrooBus.`view-model`.AccountViewModel

class AccountActivity : AppCompatActivity(), INavigable, User.UserUpdateListener {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var dialog: Dialog;
    private var viewmodel = AccountViewModel()
    private lateinit var currentId:String
    private lateinit var user: User
    private lateinit var curr_uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrUser()
        dialog = Dialog(this)
        viewmodel.getUserById(curr_uid,this){
            result->
            if(result != null){
                user = result
                user.setUpdateListener(this)
                binding.accountNameTv.text = result.name
                binding.accountEmailTv.text = result.email
                binding.accountPhoneTv.text = result.phoneNum
            }
        }

        next(binding.accountChangePasswordTv)
        back(binding.accountBackArrow)
        checkDialog(binding.accountNameEdit)
        checkDialog(binding.accountEmailEdit)
//        checkDialog(binding.accountPhoneEdit)
        checkDialog(binding.logoutBtn)
    }

    private fun checkDialog(btn: View) {
        btn.setOnClickListener {
            if (btn == binding.accountNameEdit) showDialog("Enter your name")
            if (btn == binding.accountEmailEdit) showDialog("Enter your email")
//            if (btn == binding.accountPhoneEdit) showDialog("Enter your phone")
            if(btn == binding.logoutBtn)logout()
        }

    }

    private fun getCurrUser(){
        intent = getIntent()
        if(SessionManager(this).getCurrUser().equals("")){
            curr_uid = intent.getStringExtra("CURR_UID").toString()
        }else{
            curr_uid = SessionManager(this).getCurrUser().toString()
        }
    }

    private fun logout(){
        val sessionManager = SessionManager(this)
        sessionManager.logout()
        val intent = Intent(this, GuestLandingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


    private fun showDialog(infoText: String) {

        dialog.setContentView(R.layout.dialog_account_edit)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.setCancelable(true)

        dialog.findViewById<TextView>(R.id.account_edit_tv).text = infoText

        val editTv = dialog.findViewById<EditText>(R.id.account_edit_et)
        editTv.hint = infoText

        if (infoText == "Enter your name"){
            editTv.text = binding.accountNameTv.editableText
        }
        if (infoText == "Enter your email"){
            editTv.text = binding.accountEmailTv.editableText
        }
        if (infoText == "Enter your phone"){
            editTv.text = binding.accountPhoneTv.editableText
        }

        dialog.findViewById<Button>(R.id.account_edit_btn).setOnClickListener{

            changeCredential(infoText, editTv.text.toString())
        }

        dialog.findViewById<ImageView>(R.id.account_edit_back_arrow).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun changeCredential(infoText: String, newValue: String){

        if (infoText == "Enter your name"){
            // change name
            viewmodel.updateName(curr_uid,newValue, this)
        }
        if (infoText == "Enter your email"){
            // change password
            viewmodel.updateEmail(curr_uid,newValue, this)
        }
        if (infoText == "Enter your phone"){
            viewmodel.updatePhoneNumber(curr_uid,newValue, this)
        }

        UIHelper.createToast(this, "Credential changed successfully!")
        dialog.dismiss()

    }


    override fun next(nextBtn: View) {
        nextBtn.setOnClickListener{
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("CURR_UID",curr_uid)
            startActivity(intent)
        }
    }

    override fun back(backBtn: View) {
        backBtn.setOnClickListener {
            this.finish()
        }
    }

    override fun onUpdate(user: User) {
        binding.accountNameTv.text = user.name
        binding.accountEmailTv.text = user.email
        binding.accountPhoneTv.text = user.phoneNum
    }
}