package com.pradip.news.Utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import com.pradip.news.R


class RecyclerItemClickListener(private val mRecycler: RecyclerView, private val clickListener: OnClickListener? = null,
                                private val longClickListener: OnLongClickListener? = null) : RecyclerView.OnChildAttachStateChangeListener {

    override fun onChildViewDetachedFromWindow(view: View) {
        view.setOnClickListener(null)
        view.setOnLongClickListener(null)
    }


    override fun onChildViewAttachedToWindow(view: View) {
        view.setOnClickListener { v -> setOnChildAttachedToWindow(v) }

    }

    private fun setOnChildAttachedToWindow(v: View?) {
        if (v != null) {
            val position = mRecycler.getChildLayoutPosition(v)
            if (position >= 0) {
                clickListener?.onItemClick(position, v)
                longClickListener?.onLongItemClick(position, v)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int, view: View)
    }

    interface OnLongClickListener {
        fun onLongItemClick(position: Int, view: View)
    }

//    interface AllSelectedListener{
//        fun AllSelected(position: Int, view: View)
//    }


}