package com.androidacademy.angel.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidacademy.angel.MainActivity
import com.androidacademy.angel.R
import com.androidacademy.angel.network.Repository
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

        view.log_in.setOnClickListener {
            Repository.signIn(view.email.text.toString(), view.password.text.toString()){
                isSuccessful -> Log.d("WTF", "$isSuccessful")
            }
        }


        return view
    }
}