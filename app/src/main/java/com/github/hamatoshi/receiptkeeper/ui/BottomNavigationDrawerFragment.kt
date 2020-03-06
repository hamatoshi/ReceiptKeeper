package com.github.hamatoshi.receiptkeeper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.hamatoshi.receiptkeeper.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.github.hamatoshi.receiptkeeper.databinding.FragmentBottomSheetBinding

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)

        binding.bottomSheet.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_monthly -> Toast.makeText(context, "Monthly", Toast.LENGTH_SHORT).show()
                R.id.nav_yearly -> Toast.makeText(context, "Yearly", Toast.LENGTH_SHORT).show()
                R.id.nav_statistics -> Toast.makeText(context, "Statistics", Toast.LENGTH_SHORT).show()
                R.id.nav_setting -> Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show()
            }
            true
        }

        return binding.root
    }


}