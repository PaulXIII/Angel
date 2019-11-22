package com.androidacademy.angel.registration


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidacademy.angel.R
import com.androidacademy.angel.network.Repository
import kotlinx.android.synthetic.main.login_fragment.view.*

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.registration_fragment, container, false)


        view.registration.setOnClickListener {
            Repository.signUp(view.email.text.toString(), view.password.text.toString()){
                    isSuccessful -> Log.d("WTF", "$isSuccessful")
            }
        }
        return view
    }


}
