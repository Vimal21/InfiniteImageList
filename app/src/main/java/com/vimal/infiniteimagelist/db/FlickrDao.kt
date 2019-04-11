package com.vimal.infiniteimagelist.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vimal.infiniteimagelist.model.Photo
import javax.xml.datatype.DatatypeFactory

@Dao
interface FlickrDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( photos : List<Photo>)

    @Query("SELECT * FROM photos")
    fun getPhotos() : DataSource.Factory<Int, Photo>
}