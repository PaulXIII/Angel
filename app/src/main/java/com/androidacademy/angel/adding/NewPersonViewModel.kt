package com.androidacademy.angel.adding

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidacademy.angel.network.Repository
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class NewPersonViewModel : ViewModel() {
    var photoBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()
    var error: MutableLiveData<Boolean> = MutableLiveData()
    val repository = Repository

    fun publish(title: String, description: String) {
        val stream = ByteArrayOutputStream()
        photoBitmap.value?.compress(Bitmap.CompressFormat.PNG, 15, stream)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val photoRef = storageRef.child("$title.png")
        val uploadTask = photoRef.putBytes(stream.toByteArray())
        uploadTask.addOnFailureListener {
            error.value = true
        }.addOnSuccessListener {
            val getRef = storageRef.child("$title.png")
            getRef.downloadUrl.addOnSuccessListener {
                if (it != null) repository.updateAdvert(title, description, it.toString())
                success.value = true
            }.addOnFailureListener{
                error.value = true
            }
        }
    }

    fun takePhoto(
        fragment: Fragment,
        packageManager: PackageManager
    ) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_CAMERA)
            }
        }
    }

    fun loadPhotoFromGallery(
        fragment: Fragment,
        packageManager: PackageManager
    ) {
        val photoTakerIntent: Intent = Intent(Intent.ACTION_PICK)
        photoTakerIntent.setType("image/*")
        fragment.startActivityForResult(photoTakerIntent, REQUEST_IMAGE_CAPTURE_GALERIA)


    }

    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        contentResolver: ContentResolver
    ) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_CAMERA && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            photoBitmap.value = imageBitmap
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_GALERIA && resultCode == AppCompatActivity.RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val inputSrtream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputSrtream)
                photoBitmap.value = bitmap
            }

        }
    }

    companion object {
        private val REQUEST_IMAGE_CAPTURE_CAMERA = 1
        private val REQUEST_IMAGE_CAPTURE_GALERIA = 2
    }
}