package com.pradip.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dfl.newsapi.model.ArticleDto
import com.pradip.news.R
import com.pradip.news.extensions.loadImage

/**
 * @author pradip; dated 31/03/19.
 *
 * Adapter to host content on home screen.
 * [setData] sets the data in an adapter.
 * */
class ContentListAdapter() : RecyclerView.Adapter<ContentListAdapter.ContentListViewHolder>() {
     var articles: List<ArticleDto> = arrayListOf()

    init {
        setHasStableIds(true)
    }

    fun setDataList(list: List<ArticleDto>?) {
        list?.apply {
            articles = list
            notifyItemRangeInserted(0, list.size)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentListViewHolder {
        return ContentListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ContentListViewHolder, position: Int) {
        holder.apply {

                    title?.text = articles[position].title
                    author?.text = articles[position].source.name
                    image?.loadImage { load(articles[position].urlToImage) }
                    setOnclick(position)

        }
    }

    private fun setOnclick(position: Int) {


    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ContentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var image: ImageView? = null
        internal var title: TextView? = null
        internal var author: TextView? = null

        init {
            title = itemView.findViewById(R.id.tvTitle)
            image = itemView.findViewById(R.id.ivImage)
            author = itemView.findViewById(R.id.tvAuthor)
        }

        companion object {
            fun create(parent: ViewGroup): ContentListViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_content, parent, false)
                return ContentListViewHolder(view)
            }
        }
    }
}


