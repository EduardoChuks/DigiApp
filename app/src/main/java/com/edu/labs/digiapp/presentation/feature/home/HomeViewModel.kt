package com.edu.labs.digiapp.presentation.feature.home

import androidx.lifecycle.MutableLiveData
import com.edu.labs.digiapp.domain.interactor.UseCaseResult
import com.edu.labs.digiapp.domain.model.Article
import com.edu.labs.digiapp.domain.repository.HomeRepository
import com.edu.labs.digiapp.presentation.common.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {

    val homeData = MutableLiveData<MutableList<Article>>()

    fun getHomeData() {
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = true
            val result = withContext(Dispatchers.IO) { homeRepository.recentlyArticles() }
            loading.value = false
            when (result) {
                is UseCaseResult.Success -> homeData.value = result.data
                is UseCaseResult.Error -> errorMessage.value = result.errorMsg
            }
        }
    }

    fun deleteArticle(article: Article?) {
        CoroutineScope(Dispatchers.IO).launch {
            homeRepository.deleteArticle(article)
        }
    }

}