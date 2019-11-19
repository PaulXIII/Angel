package com.androidacademy.angel.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidacademy.angel.fragments.FragmentList

class GlobalViewModel : ViewModel() {
    private val goToNextFragment: MutableLiveData<FragmentList> = MutableLiveData()
    internal val nextFragment: LiveData<FragmentList> = goToNextFragment

    internal fun pushNextFragmentName(newFragment: FragmentList) {
        goToNextFragment.value = newFragment
    }
}