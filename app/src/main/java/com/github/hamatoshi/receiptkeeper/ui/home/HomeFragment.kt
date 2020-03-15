package com.github.hamatoshi.receiptkeeper.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.github.hamatoshi.receiptkeeper.R
import com.github.hamatoshi.receiptkeeper.data.ReceiptDatabase
import com.github.hamatoshi.receiptkeeper.databinding.FragmentHomeBinding
import com.github.hamatoshi.receiptkeeper.ui.BottomNavigationDrawerFragment
import com.github.hamatoshi.receiptkeeper.ui.MainActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            FragmentHomeBinding.inflate(layoutInflater, container, false)
        val application = requireNotNull(this.activity).application
        val receiptDatabase = ReceiptDatabase.getInstance(application)
        val receiptSummaryDatabaseDao = receiptDatabase.receiptSummaryDatabaseDao
        val receiptContentDatabaseDao = receiptDatabase.receiptContentDatabaseDao
        val viewModelFactory = HomeViewModel.Factory(receiptSummaryDatabaseDao, receiptContentDatabaseDao)
        val homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val adapter = ReceiptAdapter()
        val layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(binding.listReceipt.context, layoutManager.orientation)

        // remove ripple effect of CardView
        (binding.listReceipt.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.run {
            listReceipt.adapter = adapter
            listReceipt.layoutManager = layoutManager
            listReceipt.addItemDecoration(dividerItemDecoration)
            viewModel = homeViewModel
        }

        binding.lifecycleOwner = this

        homeViewModel.receipts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        homeViewModel.navigateToInput.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeToInput())
                homeViewModel.doneNavigating()
            }
        })

        // set fab action
        val mainActivity = (requireActivity() as MainActivity)
        mainActivity.setUpMainFab { homeViewModel.onFabClicked() }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_appbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.appbar_search -> Toast.makeText(context, "Search clicked", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                val bottomNavigationDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavigationDrawerFragment.show(parentFragmentManager, bottomNavigationDrawerFragment.tag)
            }
        }
        return true
    }
}
