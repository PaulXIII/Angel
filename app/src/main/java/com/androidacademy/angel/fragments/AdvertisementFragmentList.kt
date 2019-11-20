package com.androidacademy.angel.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.androidacademy.angel.Const
import com.androidacademy.angel.R
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.network.Repository
import java.io.InputStream
import java.net.URL


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

class DownloadImageTask(private val bmImage: ImageView, private val placeHolder: Bitmap) :
    AsyncTask<String?, Void?, Bitmap>() {

    override fun onPostExecute(result: Bitmap) {
        bmImage.setImageBitmap(result)
    }

    override fun doInBackground(vararg urldisplay: String?): Bitmap {

        try {
            val stream: InputStream = URL(urldisplay.first()).openStream()
            return BitmapFactory.decodeStream(stream)
        } catch (e: Exception) {
            Log.e(Const.TAG, e.message as String)
            e.printStackTrace()
        }
        return placeHolder
    }
}