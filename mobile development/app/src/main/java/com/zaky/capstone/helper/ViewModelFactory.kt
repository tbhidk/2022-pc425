package com.zaky.capstone.helper

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaky.capstone.MainViewModel
import com.zaky.capstone.ui.gallery.GalleryViewModel
import com.zaky.capstone.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val view : View) : ViewModelProvider.NewInstanceFactory(){
    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory? = null
        @JvmStatic
        fun getInstance(view: View) : ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(view)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun<T:ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            return GalleryViewModel(view) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(view) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(view) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}