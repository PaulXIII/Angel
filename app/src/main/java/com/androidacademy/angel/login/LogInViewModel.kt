package com.androidacademy.angel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidacademy.angel.network.Repository

class LogInViewModel: ViewModel(){
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private val _emailIsValid: MutableLiveData<Boolean> = MutableLiveData()
    private val _passwordIsValid: MutableLiveData<Boolean> = MutableLiveData()

    val emailIsValid: LiveData<Boolean>
    get() = _emailIsValid

    val passwordIsValid: LiveData<Boolean>
    get() = _passwordIsValid

    fun checkEmail(){
        _emailIsValid.value = email.value?.contains("@") ?: false
    }

    fun checkPassword(){
        _passwordIsValid.value = password.value?.length ?: 0 >= 8
    }

    fun logIn(){
        checkEmail()
        checkPassword()
        if(_emailIsValid.value == true && _passwordIsValid.value == true){
            Repository.signIn(email.value ?: "", password.value ?: ""){
                isSuccessful ->  completionListener(isSuccessful)
            }
        }
    }

    private val _registrationEvent: MutableLiveData<Boolean> = MutableLiveData()
    val registationEvent: LiveData<Boolean>
    get() = _registrationEvent

    fun moveToRegistration(){
        _registrationEvent.value = true
    }

    fun resetEvent(){
        _registrationEvent.value = false
    }


    private val _logInEvent: MutableLiveData<Boolean> = MutableLiveData()
    val logInEvent: LiveData<Boolean>
    get() = _logInEvent

    private fun completionListener(result: Boolean){
        _logInEvent.value = result
    }

}