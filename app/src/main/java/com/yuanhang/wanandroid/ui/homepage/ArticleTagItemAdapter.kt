package com.yuanhang.wanandroid.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.model.Tag

/**
 * created by yuanhang on 2022/2/21
 * description:
 */
class ArticleTagItemAdapter(val articleTags: List<Tag>): RecyclerView.Adapter<ArticleTagItemAdapter.ArticleTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleTagViewHolder {
        return ArticleTagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article_tags,parent, false))
    }

    override fun onBindViewHolder(holder: ArticleTagViewHolder, position: Int) {
        holder.bind(articleTags[position])
    }

    override fun getItemCount(): Int = articleTags.size

    inner class ArticleTagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val btnTag: Button = itemView.findViewById(R.id.btnTag)

        fun bind(articleTag: Tag) {
            btnTag.text = articleTag.name
        }
    }
}