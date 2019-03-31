package com.pradip.news.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dfl.newsapi.model.ArticleDto
import com.pradip.news.Constants
import com.pradip.news.R
import com.pradip.news.Utils.RecyclerItemClickListener
import com.pradip.news.databinding.ItemGroupContentBinding
import com.pradip.news.extensions.addOnItemClick
import com.pradip.news.ui.activity.WebViewActivity

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

            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(Constants.NEWS_TITLE, adapter.articles[position].title)
            intent.putExtra(Constants.NEWS_LINK, adapter.articles[position].url)

            context.startActivity(intent)

//            Toast.makeText(view.context, "item click", Toast.LENGTH_LONG).show()
//            addFragment(WebViewFragment.newInstance(adapter.articles[position].title, adapter.articles[position].url))

        }

//        private fun addFragment(fragment: WebViewFragment) {
//            (context as AppCompatActivity).supportFragmentManager.beginTransaction().add(android.R.id.content, fragment).commit()
//        }
    })


}




