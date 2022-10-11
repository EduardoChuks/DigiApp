package com.edu.labs.digiapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edu.labs.digiapp.domain.model.Article
import com.edu.labs.digiapp.domain.model.DeletedArticle

@Database(entities = [Article::class, DeletedArticle::class], version = 12, exportSchema = false)
abstract class ArticlesDB : RoomDatabase() {
    abstract val articlesDao: ArticlesDao
    abstract val deletedArticlesDao: DeletedArticlesDao
}