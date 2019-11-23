package com.androidacademy.angel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidacademy.angel.adding.NewPersonFragment
import com.androidacademy.angel.advertisementListPackage.AdvertisementFragmentList
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.details.DetailsFragment
import com.androidacademy.angel.login.LoginFragment
import com.androidacademy.angel.registration.RegistrationFragment

class FragmentController {
    private val goToNextFragment: MutableLiveData<Fragment> = MutableLiveData()
    internal val nextFragment: LiveData<Fragment> = goToNextFragment

    private fun pushNextFragment(newFragment: Fragment) {
        goToNextFragment.value = newFragment
    }

    internal fun openLogin() {
        pushNextFragment(LoginFragment())
    }

    internal fun openRegistration(){
        pushNextFragment(RegistrationFragment())
    }

    internal fun openAdvertisementList() {
        pushNextFragment(AdvertisementFragmentList())
    }

    internal fun openDetails(advertModel: AdvertModel){
        pushNextFragment(DetailsFragment.newInstance(advertModel))
    }

    internal fun openAddNewPerson(){
        pushNextFragment(NewPersonFragment())
    }
}