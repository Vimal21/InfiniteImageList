package com.vimal.infiniteimagelist.service

import com.google.gson.annotations.SerializedName
import com.vimal.infiniteimagelist.model.Photo

data class FlickerResponse(
    @SerializedName("stat") val status : String = "",
    @SerializedName("photos") val response: PhotosReponse
)

data class PhotosReponse(
    @SerializedName("total") val totalCount : Int? =0,
    @SerializedName("photo") val items : List<Photo> = emptyList()
)