package com.androidacademy.angel.login

import android.os.Bundle
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
import com.androidacademy.angel.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
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


        viewModel.registationEvent.observe(viewLifecycleOwner, Observer { event ->
            if (event) {
                (activity as MainActivity).fragmentController.openRegistration()
                viewModel.resetEvent()
            }
        })

        viewModel.logInEvent.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                (this.activity as MainActivity).fragmentController.openAdvertisementList()
            } else {
                val string: String = getString(R.string.log_in_error)
                Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.emailIsValid.observe(viewLifecycleOwner, Observer { isValid ->
            if (isValid) {
                binding.emailLayout.isErrorEnabled = false
            } else {
                binding.emailLayout.error = getString(R.string.wrong_email)
            }
        })

        viewModel.passwordIsValid.observe(viewLifecycleOwner, Observer { isValid ->
            if (isValid) {
                binding.passwordLayout.isErrorEnabled = false
            } else {
                binding.passwordLayout.error = getString(R.string.short_password)
            }
        })

        return binding.root
    }
}