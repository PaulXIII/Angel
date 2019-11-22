package com.androidacademy.angel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidacademy.angel.advertisementListPackage.MenuItemName
import com.androidacademy.angel.advertisementListPackage.OptionMenuViewModel


class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController = FragmentController()
    private lateinit var optionMenuViewModel: OptionMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        optionMenuViewModel = ViewModelProviders.of(this).get(OptionMenuViewModel::class.java)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ad_list_main_menu, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu_filter -> optionMenuViewModel.optionClicked(MenuItemName.FILTER)
            R.id.main_menu_settings -> optionMenuViewModel.optionClicked(MenuItemName.SETTINGS)
            R.id.main_menu_search -> optionMenuViewModel.optionClicked(MenuItemName.SEARCH)
            else -> Toast.makeText(this, "Ooooops", Toast.LENGTH_SHORT).show()
        }
        return true
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