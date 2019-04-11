package com.vimal.infiniteimagelist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vimal.infiniteimagelist.model.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class FlickrDatabase : RoomDatabase(){
    abstract fun flickrDao() : FlickrDao

    companion object {
        @Volatile
        private var INSTANCE : FlickrDatabase? = null

        fun getInstance(context: Context) : FlickrDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildRoomDatabase(context).also { INSTANCE = it }
            }

        private fun buildRoomDatabase(context: Context)  =
                Room.databaseBuilder(context.applicationContext,
                    FlickrDatabase::class.java, "Flicker.db")
                    .build()
    }
}