package com.vimal.infiniteimagelist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vimal.infiniteimagelist.R
import com.vimal.infiniteimagelist.model.Photo
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory


class ImageListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val title : TextView = view.findViewById(com.vimal.infiniteimagelist.R.id.photo_name)
    private val imageView: ImageView = view.findViewById(com.vimal.infiniteimagelist.R.id.photos)

    private var photo : Photo? = null

    fun bind(photo : Photo?){
        if(photo == null){
            val resources = itemView.resources
            title.text = resources.getString(com.vimal.infiniteimagelist.R.string.no_title)
            imageView.visibility = View.GONE
        } else {
            showFlickrData(photo)
        }
    }

    private fun showFlickrData(photo: Photo) {
        this.photo = photo
        title.text = photo.title

        Glide.
            with(imageView).
            load(photo.imageUrl).
            placeholder(R.mipmap.ic_launcher).
            diskCacheStrategy(DiskCacheStrategy.ALL).
            into(imageView)
    }

    companion object {
        fun create(parent: ViewGroup): ImageListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(com.vimal.infiniteimagelist.R.layout.item_photos_lay, parent, false)
            return ImageListViewHolder(view)
        }
    }
}