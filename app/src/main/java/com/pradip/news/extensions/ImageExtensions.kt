package com.pradip.news.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pradip.news.R

/**
 * Created by pradip on 31/03/19.
 *
 * Loads the image using Glide.
 */

inline fun ImageView.loadImage(
    requestManager: RequestManager = Glide.with(this),
    func:
    RequestManager.() -> RequestBuilder<Drawable>
) {
    this.context?.let {
        requestManager
            .func()
            .apply(
                RequestOptions().placeholder(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_launcher_background
                    )
                )
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(this)
    }
}
