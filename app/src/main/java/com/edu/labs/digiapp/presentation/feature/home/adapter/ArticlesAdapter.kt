package com.edu.labs.digiapp.presentation.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edu.labs.digiapp.R
import com.edu.labs.digiapp.databinding.RowArticleBinding
import com.edu.labs.digiapp.domain.model.Article
import com.edu.labs.digiapp.presentation.feature.home.ArticleClickListener

class ArticlesAdapter(
    val items: MutableList<Article> = mutableListOf(),
    private val onArticleClicked: ArticleClickListener
) : Adapter<ArticlesAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = DataBindingUtil.inflate<RowArticleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_article, parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newData: MutableList<Article>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(
        private val binding: RowArticleBinding
    ) : ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.article = article
            binding.onClickListener = onArticleClicked
        }

    }
}