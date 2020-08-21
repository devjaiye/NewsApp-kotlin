package com.dev_app.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_app.mvvmnewsapp.models.NewsResponse
import com.dev_app.mvvmnewsapp.repository.NewsRepository
import com.dev_app.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel (
    val newsRository: NewsRepository
): ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage =  1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsPage =  1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response  = newsRository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handlerBreakingNewsResponse(response))
    }


    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handlerSearchNewsResponse(response))
    }

    private fun handlerBreakingNewsResponse(response: Response<NewsResponse>) :
            Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let{resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handlerSearchNewsResponse(response: Response<NewsResponse>) :
            Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let{resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}