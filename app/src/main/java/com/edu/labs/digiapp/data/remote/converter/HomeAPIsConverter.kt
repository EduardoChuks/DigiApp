package com.edu.labs.digiapp.data.remote.converter

import com.edu.labs.digiapp.data.remote.model.HitResponse
import com.edu.labs.digiapp.domain.model.Article
import com.edu.labs.digiapp.util.DateUtil
import com.edu.labs.digiapp.util.DateUtil.yyyy_MM_ddTHH_mm_ss_SSS_Z

object HomeAPIsConverter {

    private const val NO_TITLE = "No Title"
    private const val NO_AUTHOR = "No Author"

    fun convert(hits: List<HitResponse>): MutableList<Article> {
        val articles = mutableListOf<Article>()
        hits.forEach { hit ->
            articles.add(
                Article().apply {
                    val createdAtDate = DateUtil.parseDate(yyyy_MM_ddTHH_mm_ss_SSS_Z, hit.createdAt)
                    title =
                        if (!hit.storyTitle.isNullOrEmpty()) hit.storyTitle
                        else if (!hit.title.isNullOrEmpty()) hit.title
                        else NO_TITLE
                    createdAt = createdAtDate?.time
                    author = if (hit.author.isNullOrEmpty()) NO_AUTHOR else hit.author
                    articleSubtitle = "$author - ${DateUtil.formatArticleDate(createdAtDate)}"
                    url = if (!hit.storyUrl.isNullOrEmpty()) hit.storyUrl else hit.url
                    key = "${hit.storyId}.$title.$author".lowercase()
                }
            )
        }
        return articles
    }

}