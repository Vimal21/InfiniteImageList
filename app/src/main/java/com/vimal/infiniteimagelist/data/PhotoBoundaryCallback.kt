package com.vimal.infiniteimagelist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.vimal.infiniteimagelist.db.FlickrLocalCache
import com.vimal.infiniteimagelist.model.Photo
import com.vimal.infiniteimagelist.service.FlickrService
import com.vimal.infiniteimagelist.service.getFlickrPhotos

/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class PhotoBoundaryCallback(
    private val service: FlickrService,
    private val cache : FlickrLocalCache
) : PagedList.BoundaryCallback<Photo>(){
    companion object {
        private const val NETWORK_PAGE_SIZE = 100
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkError : LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.d("PhotoBoundaryCallback", "onZeroItemsLoaded")
        requestMorePhotosAndSave()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Photo) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.d("PhotoBoundaryCallback", "onItemAtEndLoaded")
        requestMorePhotosAndSave()
    }

    private fun requestMorePhotosAndSave(){
        if(isRequestInProgress) return

        isRequestInProgress = true
        getFlickrPhotos(service, lastRequestedPage,
            NETWORK_PAGE_SIZE, {
                photos -> cache.insert(photos){
                    lastRequestedPage++
                    isRequestInProgress = false
                }
        }, {
                error ->
                    _networkErrors.postValue(error)
                    isRequestInProgress = false
        })
    }
}