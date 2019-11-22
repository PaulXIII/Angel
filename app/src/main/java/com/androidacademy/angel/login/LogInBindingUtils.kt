package com.androidacademy.angel.login

import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("emailFocusChanged")
fun EditText.setEmailFocusListener(item: LogInViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkEmail()
        }
    }
}

@BindingAdapter("passwordFocusChanged")
fun EditText.setPasswordFocusListener(item: LogInViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkPassword()
        }
    }
}
