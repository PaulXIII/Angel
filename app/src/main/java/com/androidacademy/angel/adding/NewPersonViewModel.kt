package com.androidacademy.angel.adding

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidacademy.angel.network.Repository
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class NewPersonViewModel : ViewModel() {
    var photoBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    val repository = Repository
    var status: MutableLiveData<Status> = MutableLiveData()

    fun publish(title: String, description: String) {
        if (title == "" || description == "" || photoBitmap.value == null) {
            status.value = Status.DATA_ERROR
            return
        }

        val stream = ByteArrayOutputStream()
        photoBitmap.value?.compress(Bitmap.CompressFormat.PNG, 15, stream)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val photoRef = storageRef.child("${title}_${System.currentTimeMillis()}.png")
        val uploadTask = photoRef.putBytes(stream.toByteArray())
        uploadTask.addOnFailureListener {
            status.value = Status.ERROR
        }.addOnSuccessListener {
            val getRef = storageRef.child("$title.png")
            getRef.downloadUrl.addOnSuccessListener {
                if (it != null) repository.updateAdvert(title, description, it.toString())
                status.value = Status.SUCCESS
            }.addOnFailureListener {
                status.value = Status.ERROR
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
        fragment.startActivityForResult(photoTakerIntent, REQUEST_IMAGE_CAPTURE_GALLERIA)
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
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_GALLERIA && resultCode == AppCompatActivity.RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                val inputStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                photoBitmap.value = bitmap
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE_CAMERA = 1
        private const val REQUEST_IMAGE_CAPTURE_GALLERIA = 2
    }

    enum class Status {
        DATA_ERROR,
        SUCCESS,
        ERROR
    }
}