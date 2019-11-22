package com.androidacademy.angel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.network.Repository

class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController = FragmentController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Repository.adverts().observe(this, Observer<List<AdvertModel>> {
            Log.d(Const.TAG, "SIze ${it.size}")
            it.forEach {
                Log.d(Const.TAG, "Item title  ${it.title}")
                Log.d(Const.TAG, "Item url  ${it.url}")
            }

        })
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