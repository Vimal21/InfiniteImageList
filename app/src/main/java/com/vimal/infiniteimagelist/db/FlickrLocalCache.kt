package com.vimal.infiniteimagelist.db

import android.util.Log
import androidx.paging.DataSource
import com.vimal.infiniteimagelist.model.Photo
import com.vimal.infiniteimagelist.service.FlickrService
import java.util.concurrent.Executor


/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class FlickrLocalCache(
    private val flickrDao : FlickrDao,
    private val ioExceutor : Executor
){
    var photos: List<Photo> = emptyList()
    /**
     * Insert a list of photos in the database, on a background thread.
     */
    fun insert(photos : List<Photo>, insertFinished : () -> Unit){
        ioExceutor.execute {
            Log.d("FlickrLocalCache", "inserting ${photos.size} photos")
            flickrDao.insert(photos)
            insertFinished()
        }
    }

    /**
     * Request a LiveData<List<photo>> from the Dao, internal database
     */
    fun getFlickImages() : DataSource.Factory<Int, Photo>{
        return flickrDao.getPhotos()
    }
}