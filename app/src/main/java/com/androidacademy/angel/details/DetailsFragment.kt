package com.androidacademy.angel.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidacademy.angel.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {
    companion object {
        private const val PHOTO_URL = "url"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"

        fun newInstance(photo: String, title: String, description: String = "" ): Fragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putString(PHOTO_URL, photo)
            bundle.putString(TITLE, title)
            bundle.putString(DESCRIPTION, description)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.getString(PHOTO_URL)?.let {
            Glide
                .with(this)
                .load(it)
                .centerCrop()
                .placeholder(R.drawable.common_full_open_on_phone) //TODO use Angel placeholder
                .into(photo)
        }

        arguments?.getString(TITLE)?.let {
            title.text = it
        }

        arguments?.getString(DESCRIPTION)?.let {
            description.text = it
        }

        add_to_calendar.setOnClickListener{
            viewModel.addToCalendar()
        }

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

}
