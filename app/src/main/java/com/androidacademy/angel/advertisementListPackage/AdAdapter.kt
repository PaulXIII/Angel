package com.androidacademy.angel.advertisementListPackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class AdAdapter(var ads: List<AdvertModel>, private val placeHolder: Int, private val listener: OnAdvertClick) :
    ListAdapter<AdvertModel, ViewHolder>(
        AdListDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.advertisement_list_item,
                parent,
                false
            ),
            listener
        )
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ads[position], placeHolder)
    }
}

class ViewHolder(itemView: View, private val listener: OnAdvertClick) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.ad_list_item_title)
    private val photo: ImageView = itemView.findViewById(R.id.ad_list_item_photo)

    fun bind(ad: AdvertModel, placeHolder: Int) {
        title.text = ad.title
        Glide
            .with(itemView)
            .applyDefaultRequestOptions(RequestOptions.placeholderOf(placeHolder))
            .load(ad.url)
            .into(photo)
        photo.setOnClickListener{
            listener.onClick(ad)
        }
    }
}

interface OnAdvertClick{
    fun onClick(ad: AdvertModel)
}