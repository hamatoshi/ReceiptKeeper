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
import com.github.hamatoshi.receiptkeeper.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentHomeBinding.inflate(layoutInflater, container, false)
        val viewModelFactory = HomeViewModel.Factory()
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val adapter = ReceiptAdapter()
        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(binding.listReceipt.context, layoutManager.orientation)

        binding.listReceipt.adapter = adapter
        binding.listReceipt.layoutManager = layoutManager
        binding.listReceipt.addItemDecoration(dividerItemDecoration)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.receipts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}
