package com.dev_app.mvvmnewsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_app.mvvmnewsapp.R
import com.dev_app.mvvmnewsapp.adapters.NewsAdapters
import com.dev_app.mvvmnewsapp.ui.NewsActivity
import com.dev_app.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news){

        lateinit var viewModel: NewsViewModel
    lateinit var newsAdapters: NewsAdapters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapters.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView(){
        newsAdapters = NewsAdapters()
        rvSavedNews.apply {
            adapter = newsAdapters
            layoutManager = LinearLayoutManager(activity)
        }
    }


}