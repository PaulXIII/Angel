package com.androidacademy.angel

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController = FragmentController()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentController.nextFragment.observe(this, Observer<Fragment> {
            if (it == null)
                throw IllegalArgumentException("Fragment can't be null")

            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.base_fragment_layout, it
                )
                .commit()
        })

        fragmentController.openLogin()
    }

    private fun showProgressScreen() {
        val frameLayout = findViewById<FrameLayout>(R.id.requestNetworkContainer)
        frameLayout.isVisible=true
        val progressBar = findViewById<ProgressBar>(R.id.requestNetwork)
        progressBar.isVisible=true
    }

    private fun hideProgressScreen() {
        val frameLayout = findViewById<FrameLayout>(R.id.requestNetworkContainer)
        frameLayout.isVisible=false
        val progressBar = findViewById<ProgressBar>(R.id.requestNetwork)
        progressBar.isVisible=false
    }

}