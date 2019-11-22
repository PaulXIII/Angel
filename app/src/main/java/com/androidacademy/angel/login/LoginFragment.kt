package com.androidacademy.angel.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidacademy.angel.MainActivity
import com.androidacademy.angel.R
import kotlinx.android.synthetic.main.login_fragment.view.*

class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        view.registration.setOnClickListener {
            (activity as MainActivity).globalViewModel.openRegistration()
        }


        return view
    }
}