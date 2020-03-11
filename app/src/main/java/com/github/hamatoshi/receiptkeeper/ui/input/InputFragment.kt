package com.github.hamatoshi.receiptkeeper.ui.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.hamatoshi.receiptkeeper.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

}
