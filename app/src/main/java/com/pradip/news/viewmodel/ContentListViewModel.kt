package com.pradip.news.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.model.ArticlesDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.pradip.news.data.apiKey
import com.pradip.news.model.ContentParams
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * @author pradip; dated 31/03/19.
 */

class ContentListViewModel : BaseViewModel() {
    internal val result = MutableLiveData<Result<ArticlesDto>>()

    /**
     * Fetches news from sources.
     * You do not need to understand this implementation in order to complete the assignment.
     * All the updates (response) are posted to the [result] liveData.
     */
    fun fetchNews(params: ContentParams = ContentParams()) {
        NewsApiRepository(apiKey = apiKey).getEverything(
            q = params.q,
            sources = params.sources,
            domains = params.domains,
            language = params.language,
            sortBy = params.sortBy,
            pageSize = params.pageSize,
            page = params.page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .subscribe(
                {
                    result.value = Result.success(it)
                },
                {
                    Log.e("Error", it.cause?.message ?: "")
                    result.value = Result.failure(it)
                }
            ).disposeOnClear()
    }
}

