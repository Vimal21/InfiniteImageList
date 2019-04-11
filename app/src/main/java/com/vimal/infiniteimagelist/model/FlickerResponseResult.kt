package com.vimal.infiniteimagelist.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class FlickerResponseResult(
    val result : LiveData<PagedList<Photo>>,
    val error : LiveData<String>
)