package com.wizebrain.adebdriver.extensions
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wizebrain.adebdriver.R

import java.io.File


fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).placeholder(R.drawable.profile).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(this)
}


fun ImageView.loadImage(file: File?) {
    Glide.with(this).load(file).into(this)
}

fun ImageView.loadThumbnail(url: String) {
    Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).thumbnail(0.5f).into(this)
}

fun ImageView.loadImage(uri: Uri) {
    Glide.with(context).load(uri).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(this)
}

fun ImageView.loadImage(resource: Int) {
    Glide.with(context).load(resource).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(this)
}
