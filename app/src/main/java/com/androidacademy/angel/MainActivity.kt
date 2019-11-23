package com.androidacademy.angel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.androidacademy.angel.Const.INTENT_KEY
import com.androidacademy.angel.Const.OPEN_FRAGMENT
import com.androidacademy.angel.advertisementListPackage.AdvertisementFragmentList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController = FragmentController()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
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
            .setCustomAnimations(R.animator.slide_in_left,   R.animator.slide_in_right)
            .replace(
                R.id.base_fragment_layout, fragment
            )
            .commit()
    }

    fun showBackButtons(visibility: Boolean) {
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

    override fun onNewIntent(intent: Intent?) {
        Log.d("WTF", "OnNewIntent")
        val extras = intent?.extras
        if (extras?.containsKey(INTENT_KEY) == true) {
            Log.d("WTF", "${extras.getString(INTENT_KEY)}")
            fragmentController.openAdvertisementList()
        }

        super.onNewIntent(intent)
    }

    fun showProgressScreen() {
        requestNetworkContainer.isVisible = true
        requestNetwork.isVisible = true
    }

    fun hideProgressScreen() {
        val frameLayout = findViewById<FrameLayout>(R.id.requestNetworkContainer)
        frameLayout.isVisible = false
        val progressBar = findViewById<ProgressBar>(R.id.requestNetwork)
        progressBar.isVisible = false
    }

}