package com.dev_app.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dev_app.mvvmnewsapp.R
import com.dev_app.mvvmnewsapp.db.ArticleDatabase
import com.dev_app.mvvmnewsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

        lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModeProviderFactory = NewsViewProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModeProviderFactory).get(NewsViewModel::class.java)

//        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        //19407af1dc2a4eb0a842b2b9fc5cfde1 ... api key
        //navHostShow()
    }

//    private fun navHostShow() {
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.newsNavHostFragment) as NavHostFragment?
//        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment!!.navController)
//    }
}
