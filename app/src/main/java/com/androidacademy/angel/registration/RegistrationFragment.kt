package com.androidacademy.angel.registration


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidacademy.angel.MainActivity

import com.androidacademy.angel.R

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)



        return view
    }


}
