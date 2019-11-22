package com.androidacademy.angel.advertisementListPackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.MainActivity
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.details.DetailsFragment


class AdvertisementFragmentList : Fragment(), OnAdvertClick {
    override fun onClick(ad: AdvertModel) {
        fragmentManager?.let {
            (activity as MainActivity).fragmentController.openDetails(ad)
        }
    }

    private lateinit var parentActivity: AppCompatActivity
    private lateinit var adListAdapter: AdAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.advertisement_list_fragment, container, false)
        val advertisementListView: RecyclerView = view.findViewById(R.id.advertisement_list_view)
        advertisementListView.adapter = adListAdapter
        AdListFragmentLifecycleObserver(viewLifecycleOwner) { list ->
            adListAdapter.ads = list
            adListAdapter.notifyDataSetChanged()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AppCompatActivity
        adListAdapter =
            AdAdapter(
                listOf(),
                R.drawable.angel_logo,
                this
            )
    }
}