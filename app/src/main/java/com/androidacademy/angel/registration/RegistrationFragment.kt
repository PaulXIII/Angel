package com.androidacademy.angel.registration


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.androidacademy.angel.R
import com.androidacademy.angel.databinding.RegistrationFragmentBinding
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
        val binding:RegistrationFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.registration_fragment, container, false)

        val viewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.emailIsValid.observe(this, Observer { isValid ->
            if(isValid){
                binding.emailLayout.isErrorEnabled = false
            } else {
                binding.emailLayout.error = getString(R.string.wrong_email)
            }

        })

        viewModel.passwordIsValid.observe(this, Observer { isValid ->
            if(isValid){
                binding.passwordLayout.isErrorEnabled = false
            } else {
                binding.passwordLayout.error = getString(R.string.short_password)
            }

        })

        viewModel.confirmPasswordValid.observe(this, Observer {isValid ->
            if(isValid){
                binding.confirmPasswordlLayout.isErrorEnabled = false
            }else{
                 binding.confirmPasswordlLayout.error = getString(R.string.confirm_password_error)
            }

        })

        viewModel.registrationEvent.observe(this, Observer {isSuccess ->
            var string = ""
            if(isSuccess){
                string = "Успех"
            }else{
                string = getString(R.string.registration_error)
            }
            Toast.makeText(activity, string, Toast.LENGTH_LONG).show()

        })


        return binding.root
    }


}
