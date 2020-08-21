package com.dev_app.mvvmnewsapp.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_app.mvvmnewsapp.util.Resource
import com.dev_app.mvvmnewsapp.R
import com.dev_app.mvvmnewsapp.adapters.NewsAdapters
import com.dev_app.mvvmnewsapp.ui.NewsActivity
import com.dev_app.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import retrofit2.Response


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapters: NewsAdapters
    val TAG = "Breaking News Fragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapters.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }


        viewModel.breakingNews.observe(viewLifecycleOwner, Observer{ response ->
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
        rvBreakingNews.apply {
            adapter = newsAdapters
            layoutManager = LinearLayoutManager(activity)
        }
    }

    }