package com.edu.labs.digiapp.domain.repository

import com.edu.labs.digiapp.domain.interactor.UseCaseResult
import com.edu.labs.digiapp.domain.model.Article

interface HomeRepository {

    suspend fun recentlyArticles(): UseCaseResult<MutableList<Article>>
    suspend fun deleteArticle(article: Article?)

}