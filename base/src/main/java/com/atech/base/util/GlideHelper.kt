package com.atech.base.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object GlideHelper {
    fun showThumbnail(url: String, imageView: ImageView, context: Context) {
        val options = RequestOptions().centerCrop()

        val requestBuilder = Glide.with(context)
            .load(url)

        requestBuilder
            .apply(options)
            .into(imageView)
    }

    fun showBackDrop(url: String, imageView: ImageView, context: Context) {
        val options = RequestOptions()

        val requestBuilder = Glide.with(context)
            .load(url)

        requestBuilder
            .apply(options)
            .into(imageView)
    }
}