package com.sroo.astroobus.fragment.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sroo.astroobus.R
import com.sroo.astroobus.activity.user.UserBusActivity
import com.sroo.astroobus.adapter.BusHistoryAdapter
import com.sroo.astroobus.adapter.BusTicketAdapter
import com.sroo.astroobus.databinding.FragmentUserHistoryBinding
import com.sroo.astroobus.databinding.FragmentUserHomeBinding
import com.sroo.astroobus.model.BusTransaction
import com.sroo.astroobus.model.HistoryTransaction
import com.sroo.astroobus.model.UserTransaction
import com.sroo.astroobus.utils.SessionManager
import com.sroo.astroobus.`view-model`.UserTransactionViewModel
import java.util.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UserHistoryFragment : Fragment() {
    private lateinit var binding: FragmentUserHistoryBinding
    private lateinit var transactionRv: RecyclerView
    private var param1: String? = null
    private var param2: String? = null
    private var historyVM = UserTransactionViewModel()
    private lateinit var fragmentContext: Context
    private val historyTransactionList = ArrayList<HistoryTransaction>()
    private lateinit var busHistoryAdapter: BusHistoryAdapter

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

        busHistoryAdapter = BusHistoryAdapter(historyTransactionList)
        transactionRv.layoutManager = LinearLayoutManager(fragmentContext)
        transactionRv.adapter = busHistoryAdapter

        historyVM.getUserTransaction(session.getCurrUser()!!) { result ->
            if (result != null) {
                historyTransactionList.clear()
                historyTransactionList.addAll(result)
                busHistoryAdapter.notifyDataSetChanged()
            }
        }

        return binding.root
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