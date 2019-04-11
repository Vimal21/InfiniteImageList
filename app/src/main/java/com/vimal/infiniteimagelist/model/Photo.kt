package com.vimal.infiniteimagelist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey @field:SerializedName("id") val id: Long,
    @field:SerializedName("owner") val owner: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url_o") val imageUrl: String?
)