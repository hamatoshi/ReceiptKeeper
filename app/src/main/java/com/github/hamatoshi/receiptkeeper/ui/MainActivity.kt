package com.github.hamatoshi.receiptkeeper.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.github.hamatoshi.receiptkeeper.databinding.ActivityMainBinding
import com.github.hamatoshi.receiptkeeper.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.bottomAppbar)
        setUpNavController()
        setUpMainFab()

    }

    private fun setUpNavController() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                controller.graph.startDestination -> setBottomAppBarForHome()
                else -> setBottomAppBarForOthers()
            }
        }
    }

    private fun setBottomAppBarForHome() {
        binding.run {
            bottomAppbar.performShow()
            bottomAppbar.visibility = View.VISIBLE
            mainFab.show()
        }
    }

    private fun setBottomAppBarForOthers() {
        binding.run {
            bottomAppbar.performHide()
            bottomAppbar.visibility = View.GONE
            mainFab.hide()
        }
    }

    private fun setUpMainFab() {
        binding.mainFab.apply {
            setShowMotionSpecResource(R.animator.fab_show)
            setHideMotionSpecResource(R.animator.fab_hide)
            setOnClickListener {
                navController.navigate(R.id.inputFragment)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_appbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.appbar_search -> Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                val bottomNavigationDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavigationDrawerFragment.show(supportFragmentManager, bottomNavigationDrawerFragment.tag)
            }
        }
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}
