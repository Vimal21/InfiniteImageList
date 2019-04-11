package com.vimal.infiniteimagelist

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.vimal.infiniteimagelist.data.FlickrRepository
import com.vimal.infiniteimagelist.db.FlickrDatabase
import com.vimal.infiniteimagelist.db.FlickrLocalCache
import com.vimal.infiniteimagelist.service.FlickrService
import com.vimal.infiniteimagelist.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection{

    /**
     * Creates an instance of [FlickrLocalCache] based on the database DAO.
     */
    private fun provideCache(context: Context): FlickrLocalCache{
        val database = FlickrDatabase.getInstance(context)
        return FlickrLocalCache(database.flickrDao(), Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [FlickrRepository] based on the [FlickrService] and a
     * [FlickrLocalCache]
     */
    private fun provideGithubRepository(context: Context) : FlickrRepository {
        return FlickrRepository(FlickrService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context) : ViewModelProvider.Factory{
        return ViewModelFactory(provideGithubRepository(context))
    }
}