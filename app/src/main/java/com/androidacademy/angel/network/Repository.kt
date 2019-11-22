package com.androidacademy.angel.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidacademy.angel.Const
import com.androidacademy.angel.data.AdvertModel
import com.androidacademy.angel.prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object Repository {

    private const val DATA_CHILD = "data"
    private const val DATA_ADVERTS = "adverts"

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val advertsLiveData =
        MutableLiveData<List<AdvertModel>>()

    init {
        loadAdverts()
    }

    private fun loadAdverts() {
        val data = database.also {
            it.setPersistenceEnabled(true)
        }

        val reference = data
            .reference
            .child(DATA_CHILD)
            .child(DATA_ADVERTS)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "Error ${error.code} ")
                Log.e("TAG", "Error ${error.message} ")
                Log.e("TAG", "Error ${error.details} ")
            }

            override fun onDataChange(data: DataSnapshot) {
                val res = mutableListOf<AdvertModel>()
                data.children.forEach {
                    Log.d("TAG", "Item key ${it.key}")
                    val value = it.getValue(AdvertModel::class.java)
                    value?.run {
                        Log.d("TAG", "Item title ${value.title}")
                        Log.d("TAG", "Item url ${value.url}")
                        res.add(value)
                    }
                }
                advertsLiveData.postValue(res)
                Log.d("TAG", "Count ${data.childrenCount}")
                Log.d("TAG", "Count ${data.key}")
            }
        })
    }

    fun adverts(): LiveData<List<AdvertModel>> {
        return advertsLiveData
    }

    fun updateAdvert(title: String, description: String, url: String) {
        val newAdvert = AdvertModel(
            System.currentTimeMillis(),
            Const.STATUS_PROCESSING,
            title,
            description,
            url
        )
        database.reference
            .child(DATA_CHILD)
            .child(DATA_ADVERTS)
            .push()
            .setValue(newAdvert)
            .addOnSuccessListener {
                Log.d("TAG", "Update advert success")
            }.addOnFailureListener {
                Log.d("TAG", "Updated advert fail $it")
            }
    }

    fun signIn(
        email: String,
        password: String,
        completionListener: (isSuccessful: Boolean) -> Unit
    ) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            completionListener(task.isSuccessful)
            if (task.isSuccessful) {
                prefs.idClient = task.result?.user?.uid
                Log.d("WTF", "${task.result?.user?.uid}")
            }
        }
    }

    fun signUp(
        email: String,
        password: String,
        completionListener: (isSuccessful: Boolean) -> Unit
    ) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            completionListener(task.isSuccessful)
        }
    }

    fun signOut() {
        mAuth.signOut()
        prefs.idClient = null
    }


}