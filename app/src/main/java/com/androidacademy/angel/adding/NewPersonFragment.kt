package com.androidacademy.angel.adding

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidacademy.angel.MainActivity
import com.androidacademy.angel.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.add_new_fragment.*

class NewPersonFragment : Fragment() {

    private lateinit var viewModel: NewPersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_new_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewPersonViewModel::class.java)
        viewModel.photoBitmap.observe(this, Observer { image -> loadImage(image) })
        viewModel.status.observe(this, Observer { status -> updateStatus(status) })

        publish.setOnClickListener {
            (activity as MainActivity).showProgressScreen()

            val handler = Handler()
            handler.postDelayed({
                    viewModel.publish(
                        title = title.text.toString(),
                        description = description.text.toString()
                    )
                }, 500
            )
        }

        photo_from_gallery.setOnClickListener {
            activity?.let {
                viewModel.loadPhotoFromGallery(this, it.packageManager)
            }
        }

        take_photo.setOnClickListener {
            activity?.let {
                viewModel.takePhoto(this, it.packageManager)
            }
        }

        clear.setOnClickListener {
            viewModel.photoBitmap.value = null
        }
    }

    private fun updateStatus(status: NewPersonViewModel.Status) {
        (activity as MainActivity).hideProgressScreen()
        val message = when (status) {
            NewPersonViewModel.Status.DATA_ERROR -> getString(R.string.new_person_data_error)
            NewPersonViewModel.Status.ERROR -> getString(R.string.new_person_error)
            NewPersonViewModel.Status.SUCCESS -> getString(R.string.new_person_success)
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        if (status != NewPersonViewModel.Status.DATA_ERROR) {
            fragmentManager?.popBackStack()
        }
    }

    private fun loadImage(image: Bitmap?) {
        clear.visibility = if (image == null) View.GONE else View.VISIBLE
        Glide.with(this).load(image).placeholder(R.drawable.default_img).into(photo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activity?.let {
            viewModel.onActivityResult(requestCode, resultCode, data, it.contentResolver)
        }
    }
}