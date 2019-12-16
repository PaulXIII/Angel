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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class AdAdapter(
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
        holder.bind(currentList[position])
    }
}

class ViewHolder(itemView: View, private val listener: OnAdvertClick) :
    RecyclerView.ViewHolder(itemView) {
    private val surname: TextView = itemView.findViewById(R.id.ad_list_item_surname)
    private val name: TextView = itemView.findViewById(R.id.ad_list_item_name)
    private val photo: ImageView = itemView.findViewById(R.id.ad_list_item_photo)

    fun bind(ad: AdvertModel) {
        val circularProgressDrawable = CircularProgressDrawable(photo.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        val names = ad.title!!.split(" ")
        surname.text = if (names.size == 4) "${names[0]} ${names[1]}" else names[0]
        name.text =
            if (names.size == 4) "${names[2]} ${names[3]}" else if (names.size == 2) names[1] else "${names[1]} ${names[2]}"
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(40))
        Glide
            .with(itemView)
            .applyDefaultRequestOptions(
                requestOptions.placeholder(circularProgressDrawable)
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