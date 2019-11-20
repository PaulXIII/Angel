package com.androidacademy.angel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel

class AdvertisementFragmentList: Fragment() {
    private lateinit var
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.advertisement_list_fragment, container, false)
        val moviesList: RecyclerView = view.findViewById(R.id.advertisement_list_view)
        moviesList.adapter = AdAdapter()

        return view
    }
}

class AdAdapter(val ads: List<AdvertModel>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.advertisement_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ads[position])
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.ad_list_item_title)

    fun bind(ad: AdvertModel) {
        title.text = ad.title
    }
}