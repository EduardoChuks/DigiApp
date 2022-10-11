package com.edu.labs.digiapp.presentation.feature.home

import com.edu.labs.digiapp.domain.model.Article

interface ArticleClickListener {

    fun onArticleClicked(item: Article)

}