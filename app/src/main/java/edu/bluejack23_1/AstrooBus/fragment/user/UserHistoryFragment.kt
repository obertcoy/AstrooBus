package edu.bluejack23_1.AstrooBus.fragment.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_1.AstrooBus.adapter.BusHistoryAdapter
import edu.bluejack23_1.AstrooBus.databinding.FragmentUserHistoryBinding
import edu.bluejack23_1.AstrooBus.model.HistoryTransaction
import edu.bluejack23_1.AstrooBus.utils.SessionManager
import edu.bluejack23_1.AstrooBus.`view-model`.UserTransactionViewModel
import java.util.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UserHistoryFragment : Fragment() {
    private lateinit var binding: FragmentUserHistoryBinding
    private lateinit var transactionRv: RecyclerView
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var curr_uid:String
    private var historyVM = UserTransactionViewModel()
    private lateinit var fragmentContext: Context
    private val historyTransactionList = ArrayList<HistoryTransaction>()
    private lateinit var busHistoryAdapter: BusHistoryAdapter
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserHistoryBinding.inflate(inflater, container, false)
        transactionRv = binding.transactionRV
        fragmentContext = requireContext()
        val session = SessionManager(fragmentContext)
        Log.d("UserHistoryFragment", "FRAGMENT"+fragmentContext.toString())

        busHistoryAdapter = BusHistoryAdapter(historyTransactionList)
        transactionRv.layoutManager = LinearLayoutManager(fragmentContext)
        transactionRv.adapter = busHistoryAdapter
        getCurrUser()
        historyVM.getUserTransaction(curr_uid) { result ->
            if (result != null) {
                Log.d("UserHistoryFragment", "ini datanya ga null")
                Log.d("UserHistoryFragment", "${session.getCurrUser()!!}")
                Log.d("UserHistoryFragment", "Data size: ${historyTransactionList.size}")
                busHistoryAdapter.notifyDataSetChanged()

                historyTransactionList.clear()
                historyTransactionList.addAll(result)
                busHistoryAdapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    private fun getCurrUser(){
        val receivedValue = arguments?.getString("CURR_UID")
        if (receivedValue != null) {
            curr_uid = receivedValue
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}