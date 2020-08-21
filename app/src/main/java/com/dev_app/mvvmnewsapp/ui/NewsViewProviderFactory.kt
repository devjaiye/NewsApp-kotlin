package com.dev_app.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev_app.mvvmnewsapp.repository.NewsRepository

class NewsViewProviderFactory (
    val newsRepository : NewsRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}