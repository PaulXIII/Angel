package com.androidacademy.angel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.androidacademy.angel.Const.INTENT_KEY
import com.androidacademy.angel.Const.OPEN_FRAGMENT

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

    override fun onNewIntent(intent: Intent?) {
        Log.d("WTF", "OnNewIntent")
        val extras = intent?.extras
        if(extras?.containsKey(INTENT_KEY) == true){
            Log.d("WTF", "${extras.getString(INTENT_KEY)}")
            fragmentController.openAdvertisementList()
        }

        super.onNewIntent(intent)
    }
}