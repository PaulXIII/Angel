package com.androidacademy.angel.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidacademy.angel.network.Repository

class RegistrationViewModel:ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = MutableLiveData()

    private val _emailIsValid: MutableLiveData<Boolean> = MutableLiveData()
    val emailIsValid:LiveData<Boolean>
    get() = _emailIsValid


    private val _passwordIsValid:MutableLiveData<Boolean> = MutableLiveData()
    val passwordIsValid:LiveData<Boolean>
    get() = _passwordIsValid

    private val _confirmPasswordIsValid: MutableLiveData<Boolean> = MutableLiveData()
    val confirmPasswordValid: LiveData<Boolean>
    get() = _confirmPasswordIsValid

    fun checkEmail(){
        _emailIsValid.value = email.value?.contains("@")
    }

    fun checkPassword(){
        _passwordIsValid.value = password.value?.length ?: 0 >= 8
    }

    fun checkConfirmPassword(){
        _confirmPasswordIsValid.value = password.value  == confirmPassword.value
    }


    private val _registrationEvent: MutableLiveData<Boolean> = MutableLiveData()
    val registrationEvent:LiveData<Boolean>
    get() = _registrationEvent

    fun register(){
        checkEmail()
        checkPassword()
        checkConfirmPassword()
        if(_emailIsValid.value == true && _passwordIsValid.value == true
            && _confirmPasswordIsValid.value == true){
            Repository.signUp(email.value ?: "", password.value ?: ""){
                result-> completeListener(result)
            }
        }

    }

    private fun completeListener(result: Boolean){
        _registrationEvent.value = result
        Repository.signIn(email.value ?: "", password.value ?: ""){
                isSuccessful ->  signInCompleteListener(isSuccessful)

        }
    }

    private fun signInCompleteListener(result: Boolean){
        //TODO: Переход на рабочие экраны
    }
}