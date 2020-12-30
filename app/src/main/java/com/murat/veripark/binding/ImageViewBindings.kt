package com.murat.veripark.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.load(url)
}


@BindingAdapter("app:clickable")
fun bindButtonTitle(imageView: ImageView, clickable: Boolean) {
    imageView.isClickable = clickable
}

@BindingAdapter("imageDrawable")
fun loadImage(imageView: ImageView, url: Drawable?) {
    imageView.setImageDrawable(url)
}