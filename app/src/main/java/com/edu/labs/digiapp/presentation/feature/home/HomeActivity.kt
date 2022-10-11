package com.edu.labs.digiapp.presentation.feature.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.edu.labs.digiapp.databinding.ActivityHomeBinding
import com.edu.labs.digiapp.domain.model.Article
import com.edu.labs.digiapp.presentation.common.BaseNetworkActivity
import com.edu.labs.digiapp.presentation.feature.detail.DetailActivity
import com.edu.labs.digiapp.presentation.feature.home.adapter.ArticlesAdapter
import com.edu.labs.digiapp.util.extensions.gone
import com.edu.labs.digiapp.util.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseNetworkActivity(), ArticleClickListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var articlesAdapter: ArticlesAdapter
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObservers()
        setupRefresh()
        setupDelete()
        homeViewModel.getHomeData()
    }

    private fun setupBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        offlineView = binding.offlineCard
    }

    private fun setupObservers() {
        homeViewModel.homeData.observe(this) { articles ->
            if (!::articlesAdapter.isInitialized) {
                articlesAdapter = ArticlesAdapter(articles, this)
                binding.articlesRecycler.adapter = articlesAdapter
            } else {
                articlesAdapter.updateData(articles)
            }
        }

        homeViewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.articlesRecycler.gone()
                binding.loading.visible()
            } else {
                binding.loading.gone()
                binding.articlesRecycler.visible()
            }
        }

        homeViewModel.errorMessage.observe(this) { errorMsg ->
            Toast.makeText(this@HomeActivity, errorMsg, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRefresh() {
        binding.articlesRefresh.setOnRefreshListener {
            binding.articlesRefresh.isRefreshing = false
            if (homeViewModel.loading.value == false) homeViewModel.getHomeData()
        }
    }

    private fun setupDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.bindingAdapterPosition
                val article = articlesAdapter.items[position]
                homeViewModel.deleteArticle(article)
                articlesAdapter.items.removeAt(position)
                articlesAdapter.notifyItemRemoved(position)
                Toast.makeText(this@HomeActivity, "Article deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.articlesRecycler)
    }

    override fun onArticleClicked(item: Article) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra("url", item.url)
        startActivity(detailIntent)
    }
}