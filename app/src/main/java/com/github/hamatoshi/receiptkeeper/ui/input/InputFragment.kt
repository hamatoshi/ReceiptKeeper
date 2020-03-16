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
import com.github.hamatoshi.receiptkeeper.databinding.FragmentInputBinding
import com.github.hamatoshi.receiptkeeper.ui.MainActivity

class InputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater, container, false)

        val viewModelFactory = InputViewModel.Factory()
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

        // set fab action
        val mainActivity = (requireActivity() as MainActivity)
        mainActivity.setUpMainFab { inputViewModel.onFabClicked() }

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
