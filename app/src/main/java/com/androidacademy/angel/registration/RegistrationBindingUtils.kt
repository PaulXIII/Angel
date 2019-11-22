package com.androidacademy.angel.registration

import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("emailFocusChanged")
fun EditText.setEmailFocusListener(item: RegistrationViewModel)     {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkEmail()
        }
    }
}

@BindingAdapter("passwordFocusChanged")
fun EditText.setPasswordFocusListener(item: RegistrationViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkPassword()
        }
    }
}

@BindingAdapter("confirmPasswordFocusChanged")
fun EditText.setConfirmPasswordFocusListener(item: RegistrationViewModel){
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkConfirmPassword()
        }
    }
}