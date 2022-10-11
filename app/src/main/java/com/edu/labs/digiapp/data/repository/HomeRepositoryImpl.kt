package com.edu.labs.digiapp.data.repository

import com.edu.labs.digiapp.common.Constants.GENERAL_ERROR
import com.edu.labs.digiapp.common.Constants.NO_DATA_FOUND
import com.edu.labs.digiapp.data.local.ArticlesDao
import com.edu.labs.digiapp.data.local.DeletedArticlesDao
import com.edu.labs.digiapp.data.remote.api.HomeAPIs
import com.edu.labs.digiapp.data.remote.converter.HomeAPIsConverter
import com.edu.labs.digiapp.data.util.NetworkManager
import com.edu.labs.digiapp.domain.interactor.UseCaseResult
import com.edu.labs.digiapp.domain.model.Article
import com.edu.labs.digiapp.domain.model.DeletedArticle
import com.edu.labs.digiapp.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val homeAPI: HomeAPIs,
    private val articlesDao: ArticlesDao,
    private val deletedArticlesDao: DeletedArticlesDao,
    private val networkManager: NetworkManager
) : HomeRepository {

    override suspend fun recentlyArticles(): UseCaseResult<MutableList<Article>> {
        return if (networkManager.checkForInternet()) {
            getOnlineData()
        } else {
            getLocalData()
        }
    }

    private suspend fun getOnlineData(): UseCaseResult<MutableList<Article>> {
        try {
            homeAPI.recentlyArticles()?.hits?.let { response ->
                val homeData = HomeAPIsConverter.convert(response)
                val homeDataFiltered = filterDeleted(homeData)
                articlesDao.clearTable()
                return if (homeDataFiltered.isNotEmpty()) {
                    articlesDao.addArticles(homeDataFiltered)
                    UseCaseResult.Success(homeDataFiltered)
                } else UseCaseResult.Error(NO_DATA_FOUND)
            } ?: run { return UseCaseResult.Error(NO_DATA_FOUND) }
        } catch (e: Exception) {
            e.printStackTrace()
            return UseCaseResult.Error(e.localizedMessage ?: GENERAL_ERROR)
        }
    }

    private fun getLocalData(): UseCaseResult<MutableList<Article>> {
        try {
            articlesDao.getAllArticles()?.let { it ->
                return if (it.isNotEmpty()) {
                    val articles = it.toMutableList()
                    articles.sortByDescending { it.createdAt }
                    UseCaseResult.Success(articles)
                } else UseCaseResult.Error(NO_DATA_FOUND)
            } ?: run { return UseCaseResult.Error(NO_DATA_FOUND) }
        } catch (e: Exception) {
            e.printStackTrace()
            return UseCaseResult.Error(e.localizedMessage ?: GENERAL_ERROR)
        }
    }

    private fun filterDeleted(articles: List<Article>): MutableList<Article> {
        return try {
            val deletedKeys = deletedArticlesDao.getAllDeletedArticles()?.map { it.key }
            if (!deletedKeys.isNullOrEmpty()) {
                articles.filter { !deletedKeys.contains(it.key) }.toMutableList()
            } else articles.toMutableList()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    override suspend fun deleteArticle(article: Article?) {
        try {
            article?.let {
                articlesDao.deleteArticle(it)
                deletedArticlesDao.addDeletedArticle(DeletedArticle(it.key))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}