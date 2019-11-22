package com.androidacademy.angel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController = FragmentController()

    override fun onCreate(savedInstanceState: Bundle?) {
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
}