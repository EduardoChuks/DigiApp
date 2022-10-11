package com.edu.labs.digiapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edu.labs.digiapp.domain.model.DeletedArticle

@Dao
interface DeletedArticlesDao {

    @Query("select * from deletedArticlesTable")
    fun getAllDeletedArticles(): List<DeletedArticle>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeletedArticle(deletedArticle: DeletedArticle)

}