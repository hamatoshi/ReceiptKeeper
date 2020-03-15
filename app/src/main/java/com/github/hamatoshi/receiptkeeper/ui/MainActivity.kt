package com.github.hamatoshi.receiptkeeper.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.github.hamatoshi.receiptkeeper.databinding.ActivityMainBinding
import com.github.hamatoshi.receiptkeeper.R
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.bottomAppbar)

        setUpNavController()

    }

    private fun setUpNavController() {
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                controller.graph.startDestination -> setBottomAppBarForHome()
                R.id.homeFragment -> setBottomAppBarForHome()
                R.id.inputFragment -> setBottomAppBarForInput()
                else -> setBottomAppBarForOthers()
            }
        }
    }

    private fun setBottomAppBarForHome() {
        binding.run {
            bottomAppbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            bottomAppbar.navigationIcon = getDrawable(R.drawable.ic_menu_white_24dp)
            mainFab.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp))
        }
    }

    private fun setBottomAppBarForInput() {
        binding.run {
            bottomAppbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            bottomAppbar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_white_24dp)
            mainFab.setImageDrawable(getDrawable(R.drawable.ic_save_black_24dp))
        }
    }

    private fun setBottomAppBarForOthers() {
        binding.run {
            mainFab.hide()
        }
    }

    fun setUpMainFab(clickListener: () -> Unit) {
        binding.mainFab.apply {
            setOnClickListener {
                clickListener()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}
