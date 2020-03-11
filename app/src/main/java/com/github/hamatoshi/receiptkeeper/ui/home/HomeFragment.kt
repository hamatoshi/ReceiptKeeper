package com.github.hamatoshi.receiptkeeper.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.github.hamatoshi.receiptkeeper.data.ReceiptContentDatabase
import com.github.hamatoshi.receiptkeeper.data.ReceiptDatabase
import com.github.hamatoshi.receiptkeeper.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentHomeBinding.inflate(layoutInflater, container, false)
        val application = requireNotNull(this.activity).application
        val receiptDatabaseDao =
            ReceiptDatabase.getInstance(application).receiptDatabaseDao
        val receiptContentDatabaseDao =
            ReceiptContentDatabase.getInstance(application).receiptContentDatabaseDao
        val viewModelFactory = HomeViewModel.Factory(receiptDatabaseDao, receiptContentDatabaseDao)
        val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val adapter = ReceiptAdapter()
        val layoutManager = LinearLayoutManager(activity)
        //val dividerItemDecoration = DividerItemDecoration(binding.listReceipt.context, layoutManager.orientation)

        (binding.listReceipt.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.run {
            listReceipt.adapter = adapter
            listReceipt.layoutManager = layoutManager
            viewModel = homeViewModel
            //listReceipt.addItemDecoration(dividerItemDecoration)
        }
        binding.lifecycleOwner = this

        homeViewModel.receipts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}
