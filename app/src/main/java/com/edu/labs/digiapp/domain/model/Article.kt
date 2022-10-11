package com.edu.labs.digiapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articlesTable")
data class Article(
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "author")
    var author: String? = null,
    @ColumnInfo(name = "createdAt")
    var createdAt: Long? = null,

    // For DB comparison
    @ColumnInfo(name = "key")
    @PrimaryKey(autoGenerate = false)
    var key: String = "",

    // UI model
    @ColumnInfo(name = "articleSubtitle")
    var articleSubtitle: String? = null
)