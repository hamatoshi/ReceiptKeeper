package com.github.hamatoshi.receiptkeeper.ui.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.hamatoshi.receiptkeeper.data.ReceiptDatabase
import com.github.hamatoshi.receiptkeeper.databinding.FragmentInputBinding
import com.github.hamatoshi.receiptkeeper.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class InputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val receiptDatabase = ReceiptDatabase.getInstance(application)
        val receiptSummaryDatabaseDao = receiptDatabase.receiptSummaryDatabaseDao
        val receiptContentDatabaseDao = receiptDatabase.receiptContentDatabaseDao
        val arguments = InputFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = InputViewModel.Factory(arguments.receiptId, receiptSummaryDatabaseDao, receiptContentDatabaseDao)
        val inputViewModel = ViewModelProvider(this, viewModelFactory).get(InputViewModel::class.java)

        binding.run {
            viewModel = inputViewModel
        }
        binding.lifecycleOwner = this

        inputViewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(InputFragmentDirections.actionInputToHome())
                inputViewModel.doneNavigating()
            }
        })

        binding.inputDate.setOnClickListener { showDatePickerDialog(it as TextView) }
        binding.inputTime.setOnClickListener { showTimePickerDialog(it as TextView) }

        // set fab action and hide bottom app bar
        val mainActivity = (requireActivity() as MainActivity)
        mainActivity.setUpMainFab { inputViewModel.onFabClicked() }
        mainActivity.hideBottomAppBar()

        val bottomSheet = binding.inputField
        val behavior = BottomSheetBehavior.from(bottomSheet.root)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.textAddItem.setOnClickListener {
            if (behavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                mainActivity.hideMainFab()
            } else {
                behavior.state = BottomSheetBehavior.STATE_HIDDEN
                mainActivity.showMainFab()
            }
        }
        bottomSheet.buttonCancel.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
            mainActivity.showMainFab()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return true
    }

    private fun showTimePickerDialog(textView: TextView) {
        TimePickerFragment(textView).show(parentFragmentManager, "timePicker")
    }

    private fun showDatePickerDialog(textView: TextView) {
        DatePickerFragment(textView).show(parentFragmentManager, "datePicker")
    }

}
