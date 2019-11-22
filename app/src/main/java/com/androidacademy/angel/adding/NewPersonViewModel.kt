package com.androidacademy.angel.adding

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewPersonViewModel: ViewModel() {
    var photoUrl: MutableLiveData<String> = MutableLiveData()

    fun publish(title: String, description: String){

    }

    fun takePhoto(){

    }

    fun loadPhotoFromGallery(){

    }
}