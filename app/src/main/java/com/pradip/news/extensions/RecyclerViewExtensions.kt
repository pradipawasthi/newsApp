package com.pradip.news.extensions

import androidx.recyclerview.widget.RecyclerView
import com.pradip.news.Utils.RecyclerItemClickListener

fun RecyclerView.addOnItemClick(listener: RecyclerItemClickListener.OnClickListener) {
    this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, listener))
}