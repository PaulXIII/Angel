package com.androidacademy.angel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.network.Repository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Repository.adverts().observe(this, Observer<List<AdvertModel>> {
            Log.d(Const.TAG, "SIze ${it.size}")
            it.forEach {
                Log.d(Const.TAG, "Item title  ${it.title}")
                Log.d(Const.TAG, "Item url  ${it.url}")
            }

        })
    }

}