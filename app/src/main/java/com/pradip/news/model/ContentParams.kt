package com.pradip.news.model

import com.dfl.newsapi.enums.Language
import com.dfl.newsapi.enums.SortBy
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * @author pradip; dated 31/03/19.
 *
 * Parameters to send to the news api.
 */
data class ContentParams(
    val q: String = "apple",
    val sources: String? = null,
    val domains: String? = null,
    val language: Language = Language.EN,
    val sortBy: SortBy = SortBy.PUBLISHED_AT,
    val pageSize: Int = 50,
    val page: Int = 1
)

