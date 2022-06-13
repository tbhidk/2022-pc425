package com.zaky.capstone

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaky.capstone.data.User
import com.zaky.capstone.ui.gallery.GalleryFragment

class MainViewModel(view: View) : ViewModel() {
    val db = Firebase.firestore
    val view = view
    private val currentUser = Firebase.auth.currentUser
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    init {
        cariUser()
    }

    private fun cariUser () {
        val docRef = db.collection("users")
            .whereEqualTo("email", currentUser?.email)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    document.forEach {
                        Log.d("Test", "dokumen  ${it.data}")
                        var user = User(it.get("name").toString(), it.get("email").toString(), it.get("password").toString(), it.get("idHome").toString())
                        _user.postValue(user)
                    }
                } else {
                    Snackbar.make(view, "Maaf, Ada kesalahan sistem", Snackbar.LENGTH_LONG).show()
                    Log.d(GalleryFragment.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Snackbar.make(view, "Maaf, terdapat kesalahan. Mungkin koneksi anda buruk", Snackbar.LENGTH_LONG).show()
            }
    }
}