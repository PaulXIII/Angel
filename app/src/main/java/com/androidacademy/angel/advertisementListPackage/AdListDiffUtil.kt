package com.androidacademy.angel.advertisementListPackage

import androidx.recyclerview.widget.DiffUtil
import com.androidacademy.angel.data.AdvertModel

class AdListDiffUtil: DiffUtil.ItemCallback<AdvertModel>() {
    override fun areItemsTheSame(oldItem: AdvertModel, newItem: AdvertModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AdvertModel, newItem: AdvertModel): Boolean {
        return oldItem.title == newItem.title && oldItem.url == newItem.url
    }
}