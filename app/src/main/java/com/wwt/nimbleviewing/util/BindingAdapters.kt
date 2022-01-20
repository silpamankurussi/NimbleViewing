package com.wwt.nimbleviewing.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.wwt.nimbleviewing.R

@BindingAdapter("loadImage")
fun loadImage(album_art: ImageView, url: String) {

    Log.d("loadImage", "entered")
    Picasso.get().load(url).fit().centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(album_art)
}
