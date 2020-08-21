package com.dev_app.mvvmnewsapp.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_app.mvvmnewsapp.R
import com.dev_app.mvvmnewsapp.adapters.NewsAdapters
import com.dev_app.mvvmnewsapp.ui.NewsActivity
import com.dev_app.mvvmnewsapp.ui.NewsViewModel
import com.dev_app.mvvmnewsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.dev_app.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapters: NewsAdapters
    val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapters.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job: Job? = null
        etSearch.addTextChangedListener {editable ->

            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer{ response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{ newsResponse ->
                        newsAdapters.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error-> {
                    hideProgressBar()
                    response.message?.let { message->
                        Log.e(TAG,"An Error Occurred: $message")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })

    }


    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        newsAdapters = NewsAdapters()
        rvSearchNews.apply {
            adapter = newsAdapters
            layoutManager = LinearLayoutManager(activity)
        }
    }


}