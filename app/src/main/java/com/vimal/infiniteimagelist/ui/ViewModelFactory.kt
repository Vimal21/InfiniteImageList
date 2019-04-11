package com.vimal.infiniteimagelist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vimal.infiniteimagelist.data.FlickrRepository

class ViewModelFactory(private val repository : FlickrRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImagesListViewModel::class.java)){
            return ImagesListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}