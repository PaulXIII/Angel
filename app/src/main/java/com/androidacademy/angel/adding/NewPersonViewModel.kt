package com.androidacademy.angel.adding

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewPersonViewModel: ViewModel() {
    var photoBitmap: MutableLiveData<Bitmap> = MutableLiveData()

    fun publish(title: String, description: String){

    }

    fun takePhoto(
        fragment: Fragment,
        packageManager: PackageManager
    ){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    fun loadPhotoFromGallery(){

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoBitmap.value = imageBitmap
        }
    }

    companion object {
        private val REQUEST_IMAGE_CAPTURE = 1
    }
}