package com.androidacademy.angel.adding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidacademy.angel.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.add_new_fragment.*

class NewPersonFragment : Fragment(){
    companion object {
        fun newInstance(): Fragment {
            return NewPersonFragment()
        }
    }

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
        viewModel.photoUrl.observe(this, Observer { url -> loadImage(url) })

        publish.setOnClickListener{
            viewModel.publish(title= title.text.toString(), description= description.text.toString())
        }

        photo_from_gallery.setOnClickListener{
            viewModel.loadPhotoFromGallery()
        }

        take_photo.setOnClickListener{
            viewModel.takePhoto()
        }
    }

    fun loadImage(url: String?) {
        Glide.with(this).load(url).placeholder(R.drawable.angel_logo).into(photo)
    }
}