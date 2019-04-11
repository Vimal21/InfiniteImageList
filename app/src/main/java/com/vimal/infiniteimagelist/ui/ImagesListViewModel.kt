package com.vimal.infiniteimagelist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vimal.infiniteimagelist.data.FlickrRepository
import com.vimal.infiniteimagelist.model.FlickerResponseResult
import com.vimal.infiniteimagelist.model.Photo

/**
 * ViewModel for the [ImagesListActivity] screen.
 * The ViewModel works with the [FlickrRepository] to get the data.
 */
class ImagesListViewModel(private val repository: FlickrRepository) : ViewModel() {
    private val fetchPhotos = MutableLiveData<FlickerResponseResult>()
    fun fetchImages(){
        fetchPhotos.value = repository.getFlickPhotos()
    }

    val photos : LiveData<PagedList<Photo>> = Transformations.switchMap(fetchPhotos) {
        it.result
    }

    val networkErrors : LiveData<String> = Transformations.switchMap(fetchPhotos){
        it.error
    }
}