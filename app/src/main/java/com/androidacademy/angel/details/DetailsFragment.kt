package com.androidacademy.angel.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {
    companion object {
        private const val ADVERT_MODEL = "AdvertModel"

        fun newInstance(advertModel: AdvertModel): Fragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ADVERT_MODEL, advertModel)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailsViewModel
    private var advertModel: AdvertModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        advertModel = arguments?.getParcelable<AdvertModel>(ADVERT_MODEL)

        advertModel?.let {
            Glide
                .with(this)
                .load(it.url)
                .centerCrop()
                .placeholder(R.drawable.angel_logo)
                .into(details_photo)

            title.text = it.title
            description.text = it.description
            if (it.nextEvent == null)
                add_to_calendar.hide()
        }

        add_to_calendar.setOnClickListener{
            advertModel?.let {
                viewModel.addToCalendar(context, it)
            }
        }

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

}
