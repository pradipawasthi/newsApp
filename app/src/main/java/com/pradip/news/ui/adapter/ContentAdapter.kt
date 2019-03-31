package com.pradip.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dfl.newsapi.model.ArticleDto
import com.pradip.news.R
import com.pradip.news.Utils.RecyclerItemClickListener
import com.pradip.news.databinding.ItemGroupContentBinding
import com.pradip.news.extensions.addOnItemClick

/**
 * @author pradip; dated 31/03/19.
 *
 * Adapter to host content on home screen.
 * [setData] sets the data in an adapter.
 * */
class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {
    private var articles: Map<String, List<ArticleDto>> = mutableMapOf()


    init {
        setHasStableIds(true)
    }

    fun setData(list: List<ArticleDto>) {
        with(list) {
            articles = list.groupBy { it.source.name }
        }
        notifyItemRangeInserted(0, articles.size)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {

        return ContentViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_group_content, parent, false))
    }


    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(ArrayList<String>(articles.keys)[position], articles[ArrayList<String>(articles.keys)[position]])
    }

    override fun getItemCount(): Int {
        return articles.size
    }


    class ContentViewHolder(var binding: ItemGroupContentBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(articlesGroupName: String, articlesData: List<ArticleDto>?) {
            binding.sourceName = articlesGroupName
            binding.article = articlesData
            binding.executePendingBindings()

        }
    }

}


@BindingAdapter("articleData")
fun addPlaylist(rvSource: RecyclerView, articlesData: List<ArticleDto>?) {
    val context = rvSource.context

    val adapter = ContentListAdapter()
    rvSource.layoutManager = LinearLayoutManager(rvSource.context)
    rvSource.adapter = adapter
    articlesData?.let {
        adapter.setDataList(articlesData)
    }

    rvSource.addOnItemClick(object : RecyclerItemClickListener.OnClickListener {
        override fun onItemClick(position: Int, view: View) {
            Toast.makeText(view.context, "item click", Toast.LENGTH_LONG).show()
        }
    })


}

interface ItemClick {

    fun onTagClicked(position: Int, like: Boolean, data: List<ArticleDto>)

}


