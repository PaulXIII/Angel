package com.androidacademy.angel

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.androidacademy.angel.advertisementListPackage.AdvertisementFragmentList

class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController = FragmentController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentController.nextFragment.observe(this, Observer<Fragment> {
            if (it == null)
                throw IllegalArgumentException("Fragment can't be null")

            if (it is AdvertisementFragmentList) {
                showBackButtons(false)
                showDefaultFragment(it)
            } else {
                showBackButtons(true)
                showFragment(it)
            }
        })

        if (savedInstanceState == null)
//            fragmentController.openLogin()
            fragmentController.openAdvertisementList()
    }

    private fun showDefaultFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.base_fragment_layout, fragment
            )
            .commit()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.base_fragment_layout, fragment
            )
            .commit()
    }

    private fun showBackButtons(visibility: Boolean) {
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(visibility)
            setDisplayShowHomeEnabled(visibility)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showProgressScreen() {
        val frameLayout = findViewById<FrameLayout>(R.id.requestNetworkContainer)
        frameLayout.isVisible = true
        val progressBar = findViewById<ProgressBar>(R.id.requestNetwork)
        progressBar.isVisible = true
    }

    private fun hideProgressScreen() {
        val frameLayout = findViewById<FrameLayout>(R.id.requestNetworkContainer)
        frameLayout.isVisible = false
        val progressBar = findViewById<ProgressBar>(R.id.requestNetwork)
        progressBar.isVisible = false
    }

}