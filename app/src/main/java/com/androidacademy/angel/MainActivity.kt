package com.androidacademy.angel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.fragments.FragmentList
import com.androidacademy.angel.fragments.LoginFragment
import com.androidacademy.angel.network.Repository
import com.androidacademy.angel.viewModels.GlobalViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var globalViewModel: GlobalViewModel

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

        globalViewModel = ViewModelProviders.of(this).get(GlobalViewModel::class.java)
        globalViewModel.nextFragment.observe(this, Observer<FragmentList> {
            lateinit var nextFragment: Fragment
            when (it!!) {
                FragmentList.Login -> nextFragment = LoginFragment()
            }
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.base_fragment_layout, nextFragment
                )
                .commit()
        })

        globalViewModel.pushNextFragmentName(FragmentList.Login)
    }
}