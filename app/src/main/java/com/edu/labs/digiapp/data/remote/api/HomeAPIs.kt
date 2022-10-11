package com.edu.labs.digiapp.data.remote.api

import com.edu.labs.digiapp.data.remote.model.ArticlesResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeAPIs {

    @POST(URLs.RECENTLY_ARTICLES)
    suspend fun recentlyArticles(@Query("query") query: String = "mobile"): ArticlesResponse?

}