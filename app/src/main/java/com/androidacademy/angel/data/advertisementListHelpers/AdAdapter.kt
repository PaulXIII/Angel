package com.androidacademy.angel.data.advertisementListHelpers

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.network.DownloadImageTask


class AdAdapter(var ads: List<AdvertModel>, private val placeHolder: Bitmap) :
    RecyclerView.Adapter<ViewHolder>() {
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
        holder.bind(ads[position], placeHolder)
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.ad_list_item_title)
    private val photo: ImageView = itemView.findViewById(R.id.ad_list_item_photo)

    fun bind(ad: AdvertModel, placeHolder: Bitmap) {
        title.text = ad.title
        DownloadImageTask(photo, placeHolder).execute(ad.url)
    }
}