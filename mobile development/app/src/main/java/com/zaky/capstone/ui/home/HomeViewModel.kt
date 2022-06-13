package com.zaky.capstone.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zaky.capstone.data.Chat
import com.zaky.capstone.data.User
import com.zaky.capstone.ui.gallery.GalleryFragment
import java.util.*
import kotlin.math.log

class HomeViewModel(view : View) : ViewModel() {
    val db = Firebase.firestore
    val view = view

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
    private val _terkunci = MutableLiveData<Boolean>()
    val terkunci : LiveData<Boolean> = _terkunci
    private val _kunci = MutableLiveData<Boolean>()
    val kunci : LiveData<Boolean> = _kunci
    private val _hujan = MutableLiveData<Boolean>()
    val hujan : LiveData<Boolean> = _hujan
    private val _jemur = MutableLiveData<Boolean>()
    val jemur : LiveData<Boolean> = _jemur
    private val _gasAman = MutableLiveData<Boolean>()
    val gasAman : LiveData<Boolean> = _gasAman
    private val _sekring = MutableLiveData<Boolean>()
    val sekring : LiveData<Boolean> = _sekring
    private val _lampu = MutableLiveData<Boolean>()
    val lampu : LiveData<Boolean> = _lampu
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user
    private val currentUser = Firebase.auth.currentUser

    init {
        cekRumah()
        cariUser()
    }

    private fun cekRumah () {
        val docRef = db.collection("homes")
            .document("home")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    _terkunci.postValue(document.get("terkunci").toString().toBoolean())
                    _kunci.postValue(document.get("kunci").toString().toBoolean())
                    _hujan.postValue(document.get("hujan").toString().toBoolean())
                    _jemur.postValue(document.get("jemur").toString().toBoolean())
                    _gasAman.postValue(document.get("gasAman").toString().toBoolean())
                    _sekring.postValue(document.get("sekring").toString().toBoolean())
                    _lampu.postValue(document.get("lampu").toString().toBoolean())
                } else {
                    Snackbar.make(view, "Maaf, Ada kesalahan sistem", Snackbar.LENGTH_LONG).show()
                    Log.d(GalleryFragment.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Snackbar.make(view, "Maaf, terdapat kesalahan. Mungkin koneksi anda buruk", Snackbar.LENGTH_LONG).show()
            }
    }

    fun kunci (boolean: Boolean) {
        val data = hashMapOf("kunci" to boolean)
        db.collection("homes").document("home")
            .set(data, SetOptions.merge())
        cekRumah()
    }
    fun jemur (boolean: Boolean) {
        val data = hashMapOf("jemur" to boolean)
        db.collection("homes").document("home")
            .set(data, SetOptions.merge())
        cekRumah()
    }
    fun lampu (boolean: Boolean) {
        val data = hashMapOf("lampu" to boolean)
        db.collection("homes").document("home")
            .set(data, SetOptions.merge())
        cekRumah()
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