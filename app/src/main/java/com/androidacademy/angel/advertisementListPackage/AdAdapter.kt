package com.androidacademy.angel.advertisementListPackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class AdAdapter(
    private val placeHolder: Int,
    private val listener: OnAdvertClick
) :
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], placeHolder)
    }
}

class ViewHolder(itemView: View, private val listener: OnAdvertClick) :
    RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.ad_list_item_title)
    private val photo: ImageView = itemView.findViewById(R.id.ad_list_item_photo)

    fun bind(ad: AdvertModel, placeHolder: Int) {
        val circularProgressDrawable = CircularProgressDrawable(photo.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        title.text = ad.title
        Glide
            .with(itemView)
            .applyDefaultRequestOptions(
                RequestOptions.circleCropTransform()
                    .placeholder(circularProgressDrawable)
            )
            .load(ad.url)
            .transform()
            .into(photo)

        itemView.setOnClickListener {
            listener.onClick(ad)
        }
    }
}

interface OnAdvertClick {
    fun onClick(ad: AdvertModel)
}