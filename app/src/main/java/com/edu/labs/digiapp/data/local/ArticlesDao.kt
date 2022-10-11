package com.edu.labs.digiapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edu.labs.digiapp.domain.model.Article

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articlesTable")
    fun getAllArticles(): List<Article>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticles(articles: List<Article>)

    @Delete
    fun deleteArticle(article: Article)

    @Query("DELETE FROM articlesTable")
    fun clearTable()

}