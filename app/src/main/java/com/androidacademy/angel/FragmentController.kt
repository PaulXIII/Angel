package com.androidacademy.angel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidacademy.angel.fragments.LoginFragment

class FragmentController {
    private val goToNextFragment: MutableLiveData<Fragment> = MutableLiveData()
    internal val nextFragment: LiveData<Fragment> = goToNextFragment

    private fun pushNextFragment(newFragment: Fragment) {
        goToNextFragment.value = newFragment
    }

    internal fun openLogin(){
        pushNextFragment(LoginFragment())
    }

    internal fun openAdvertisementList(){

    }
}