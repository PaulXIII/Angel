package com.androidacademy.angel.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.androidacademy.angel.Const
import java.io.InputStream
import java.net.URL

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