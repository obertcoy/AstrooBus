package edu.bluejack23_1.AstrooBus.components

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import androidx.cardview.widget.CardView
import edu.bluejack23_1.AstrooBus.R
import edu.bluejack23_1.AstrooBus.databinding.CardManageBusBinding
import edu.bluejack23_1.AstrooBus.interfaces.IDropdownable
import edu.bluejack23_1.AstrooBus.utils.LocationUtils
import edu.bluejack23_1.AstrooBus.utils.TimeUtils
import edu.bluejack23_1.AstrooBus.`view-model`.BusViewModel

class CardManageBus @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs), IDropdownable {

    private lateinit var viewModel: BusViewModel

    private lateinit var binding: CardManageBusBinding
    private lateinit var currActivity: Activity
    private lateinit var dialog: Dialog

    private lateinit var fromSelect: AutoCompleteTextView
    private lateinit var destinationSelect: AutoCompleteTextView
    private lateinit var startTimeSelect: Spinner
    private lateinit var endTimeSelect: Spinner

    init {

        LayoutInflater.from(context).inflate(R.layout.card_manage_bus, this, true)
        binding = CardManageBusBinding.inflate(LayoutInflater.from(context), this, true)
        viewModel = BusViewModel()

        if (context is Activity) {
            currActivity = context
            dialog = Dialog(currActivity)
        }

        displayDeployDialog(binding.manageBusBtn)
    }

    private fun displayDeployDialog(btn: View) {

        btn.setOnClickListener {

            dialog.setContentView(R.layout.dialog_deploy_bus)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
            dialog.setCancelable(true)

            initDropdownData()

            dialog.findViewById<Button>(R.id.deploy_bus_btn).setOnClickListener{
                submit()
            }

            dialog.show()
        }
    }

    override fun initDropdownData() {

        fromSelect = dialog.findViewById(R.id.deploy_bus_from_dropdown)
        destinationSelect =
            dialog.findViewById(R.id.deploy_bus_to_dropdown)
        startTimeSelect = dialog.findViewById(R.id.deploy_bus_time_dropdown)

        fromSelect.setAdapter(LocationUtils.getLocationsAdapter(currActivity))
        destinationSelect.setAdapter(LocationUtils.getLocationsAdapter(currActivity))

        startTimeSelect.adapter = TimeUtils.getTimeListAdapter(currActivity)
        endTimeSelect.adapter = TimeUtils.getTimeListAdapter(currActivity)

    }

    override fun submit() {

        val startingPoint = fromSelect.text.toString()
        val destinationPoint = destinationSelect.text.toString()
        var startTime = ""
        var endTime = ""

        startTimeSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                startTime = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        endTimeSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                endTime = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        dialog.findViewById<Button>(R.id.deploy_bus_btn).setOnClickListener{
            viewModel.deployBus(startingPoint, destinationPoint, startTime, endTime, currActivity)
        }

        // deploy bus here

    }




}