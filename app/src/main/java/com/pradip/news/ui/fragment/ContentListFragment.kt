package com.pradip.news.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dfl.newsapi.model.ArticleDto
import com.dfl.newsapi.model.ArticlesDto
import kotlinx.android.synthetic.main.fragment_content_list.*
import com.pradip.news.R
import com.pradip.news.extensions.observeK
import com.pradip.news.extensions.viewModel
import com.pradip.news.ui.adapter.ContentAdapter
import com.pradip.news.ui.adapter.ContentListAdapter
import com.pradip.news.viewmodel.ContentListViewModel


/**
 * @author pradip; dated 31/03/19.
 *
 * Fragment to host content list on homepage.
 *
 * It fetches data from [ContentListViewModel]
 * and shows it in a recyclerView using [ContentListAdapter].
 */
class ContentListFragment : Fragment() {

    private val viewModel: ContentListViewModel by viewModel() // lazy initialization
    private val adapter = ContentAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvContent.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = this@ContentListFragment.adapter
        }

        viewModel.result.observeK(this, ::onNewsLoaded)

        progressBar.visibility = View.VISIBLE
        viewModel.fetchNews() // fetches news
    }

    private fun onNewsLoaded(result: Result<ArticlesDto>) {
        progressBar.visibility = View.GONE
        if (result.isSuccess) {

            adapter.setData(result.getOrThrow().articles)
            // sets the data into adapter
        }

        if (result.isFailure) {
            throw result.exceptionOrNull() ?: RuntimeException("unknown error from api")
        }
    }
}
