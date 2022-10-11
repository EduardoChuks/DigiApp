package com.edu.labs.digiapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("hits") var hits: List<HitResponse>? = null
)

data class HitResponse(
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("story_id") var storyId: Int? = null,
    @SerializedName("story_title") var storyTitle: String? = null,
    @SerializedName("story_text") var storyText: String? = null,
    @SerializedName("story_url") var storyUrl: String? = null
)