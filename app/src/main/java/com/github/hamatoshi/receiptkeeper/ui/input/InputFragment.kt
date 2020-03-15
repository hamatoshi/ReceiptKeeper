package com.github.hamatoshi.receiptkeeper.ui.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        val homeViewModel = ViewModelProvider(this, viewModelFactory).get(InputViewModel::class.java)

        binding.run {
            viewModel = homeViewModel
        }
        binding.lifecycleOwner = this

        val mainActivity = (requireActivity() as MainActivity)
        mainActivity.setUpMainFab {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
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
}
