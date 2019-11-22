package com.androidacademy.angel.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidacademy.angel.MainActivity
import com.androidacademy.angel.R
import com.androidacademy.angel.advertisementListPackage.AdvertisementFragmentList
import com.androidacademy.angel.databinding.LoginFragmentBinding
import com.androidacademy.angel.network.Repository
import com.androidacademy.angel.prefs
import kotlinx.android.synthetic.main.login_fragment.view.*

class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)

        val viewModel = ViewModelProviders.of(this).get(LogInViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.registationEvent.observe(this, Observer {event ->
            if(event){
                (activity as MainActivity).fragmentController.openRegistration()
                viewModel.resetEvent()
            }

        })

        viewModel.logInEvent.observe(this, Observer {isSuccess ->
            var string = ""
            if(isSuccess){
                fragmentManager?.let {
                    val fragment = AdvertisementFragmentList()
                    it
                        .beginTransaction()
                        .replace(R.id.base_fragment_layout, fragment)
                        .commit()
                }
            }else{
                string = getString(R.string.log_in_error)
                Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
            }

        })

        viewModel.emailIsValid.observe(this, Observer {isValid ->
            if(isValid){
                binding.emailLayout.isErrorEnabled = false
            } else {
                binding.emailLayout.error = getString(R.string.wrong_email)
            }

        })

        viewModel.passwordIsValid.observe(this, Observer {isValid ->
            if(isValid){
                binding.passwordLayout.isErrorEnabled = false
            }else{
                binding.passwordLayout.error  = getString(R.string.short_password)
            }

        })

        return binding.root
    }
}