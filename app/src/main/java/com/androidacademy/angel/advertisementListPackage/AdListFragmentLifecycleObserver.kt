package com.androidacademy.angel.advertisementListPackage

import android.util.Log
import androidx.lifecycle.*
import com.androidacademy.angel.Const
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.network.Repository

class AdListFragmentLifecycleObserver(
    private val lifeCycleOwner: LifecycleOwner,
    val callback: (List<AdvertModel>) -> Unit
) : LifecycleObserver {

    private val observer = Observer<List<AdvertModel>> { list ->
        Log.d(Const.TAG, "Size ${list.size}")
        list.forEach {
            Log.d(Const.TAG, "Item id  ${it.id}")
            Log.d(Const.TAG, "Item title  ${it.title}")
            Log.d(Const.TAG, "Item url  ${it.url}")
        }
        callback(list)
    }

    init {
        lifeCycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttachToListener() {
        Log.d(Const.TAG, "AdListFragmentLifecycleObserver attached")
        Repository.adverts().observe(this.lifeCycleOwner, observer)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDeattachToListener() {
        Log.d(Const.TAG, "AdListFragmentLifecycleObserver deattached")
        Repository.adverts().removeObserver(observer)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        lifeCycleOwner.lifecycle.removeObserver(this)
    }

}