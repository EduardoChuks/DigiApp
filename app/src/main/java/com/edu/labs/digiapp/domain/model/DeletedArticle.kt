package com.edu.labs.digiapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deletedArticlesTable")
data class DeletedArticle(
    // For DB comparison
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "key")
    var key: String = "",
)