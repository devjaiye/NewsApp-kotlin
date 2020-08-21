package com.dev_app.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev_app.mvvmnewsapp.models.Article


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article) : Long
    @Query("SELECT * FROM articles")

    // Live Data
    fun getAllArticle(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)



}