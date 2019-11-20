package com.androidacademy.angel.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.Const
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.data.advertisementListHelpers.AdAdapter
import com.androidacademy.angel.network.Repository


class AdvertisementFragmentList : Fragment() {

    private lateinit var parentActivity: AppCompatActivity
    private lateinit var placeHolder: Bitmap
    private lateinit var adListAdapter: AdAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.advertisement_list_fragment, container, false)
        val advertisementListView: RecyclerView = view.findViewById(R.id.advertisement_list_view)
        advertisementListView.adapter = adListAdapter
        observeAdvertisementList()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AppCompatActivity
        this.placeHolder =
            BitmapFactory.decodeResource(parentActivity.resources, R.drawable.angel_logo)
        adListAdapter = AdAdapter(listOf(), placeHolder)
    }

    private fun observeAdvertisementList() {
        Repository.adverts().observe(this, Observer<List<AdvertModel>> {
            Log.d(Const.TAG, "Size ${it.size}")
            it.forEach {
                Log.d(Const.TAG, "Item title  ${it.title}")
                Log.d(Const.TAG, "Item url  ${it.url}")
            }
            adListAdapter.ads = it
            adListAdapter.notifyDataSetChanged()
        })
    }
}