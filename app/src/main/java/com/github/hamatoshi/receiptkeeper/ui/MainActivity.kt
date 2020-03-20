package com.github.hamatoshi.receiptkeeper.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.github.hamatoshi.receiptkeeper.databinding.ActivityMainBinding
import com.github.hamatoshi.receiptkeeper.R
import com.github.hamatoshi.receiptkeeper.util.SettingsUtils
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

        initializeTheme()

        setSupportActionBar(binding.bottomAppbar)

        setUpNavController()

    }

    private fun setUpNavController() {
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                controller.graph.startDestination -> setBottomAppBarForHome()
                R.id.homeFragment -> setBottomAppBarForHome()
                R.id.inputFragment -> setBottomAppBarForInput()
                else -> { setBottomAppBarForOthers() }
            }
        }
    }

    private fun setBottomAppBarForHome() {
        binding.run {
            if (bottomAppbar.fabAlignmentMode != BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
                bottomAppbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            } else {
                showMainFab()
            }
            bottomAppbar.navigationIcon = getDrawable(R.drawable.ic_menu_white_24dp)
            mainFab.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp))
        }
        showBottomAppbar()
    }

    private fun setBottomAppBarForInput() {
        binding.run {
            bottomAppbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            mainFab.setImageDrawable(getDrawable(R.drawable.ic_save_black_24dp))
        }
    }

    private fun setBottomAppBarForOthers() {
        hideMainFab()
    }

    fun setUpMainFab(clickListener: () -> Unit) {
        binding.mainFab.apply {
            setOnClickListener { clickListener() }
        }
    }

    fun hideMainFab() { binding.mainFab.hide() }
    fun showMainFab() { binding.mainFab.show() }

    fun hideBottomAppBar() {
        val bottomAppBarAnimation = binding.bottomAppbar.animate()
        bottomAppBarAnimation.translationY(binding.bottomAppbar.height.toFloat())
        bottomAppBarAnimation.duration = 400L
        bottomAppBarAnimation.withEndAction {
            binding.bottomAppbar.visibility = View.GONE
        }.start()
    }

    private fun showBottomAppbar() {
        val bottomAppBarAnimation = binding.bottomAppbar.animate()
        bottomAppBarAnimation.translationY(0F)
        bottomAppBarAnimation.duration = 400L
        bottomAppBarAnimation.withStartAction {
            binding.bottomAppbar.visibility = View.VISIBLE
        }.start()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initializeTheme() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = sharedPref.getString("theme", "")?.toInt()
        SettingsUtils.applyTheme(theme)
    }


}
