package com.github.hamatoshi.receiptkeeper.ui.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.hamatoshi.receiptkeeper.databinding.FragmentInputBinding

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

        return binding.root
    }
}
