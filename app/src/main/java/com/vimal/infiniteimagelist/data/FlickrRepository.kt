package com.vimal.infiniteimagelist.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vimal.infiniteimagelist.db.FlickrLocalCache
import com.vimal.infiniteimagelist.model.FlickerResponseResult
import com.vimal.infiniteimagelist.service.FlickrService

class FlickrRepository(
    private val service: FlickrService,
    private val flickrLocalCache: FlickrLocalCache
) {
    /**
     * Get the photos or images from the Flickr API
     */
    fun getFlickPhotos() : FlickerResponseResult{

        // Get data source factory from the local cache
        val dataSourceFactory = flickrLocalCache.getFlickImages()

        // every new api hit creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback = PhotoBoundaryCallback( service, flickrLocalCache)
        val networkError = boundaryCallback.networkError

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
            .setPageSize(INITIAL_PAGE_SIZE)
            .build()

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return FlickerResponseResult(data, networkError)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
        private const val INITIAL_PAGE_SIZE = 10
    }
}