package com.yuanhang.wanandroid.ui.homepage

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.util.gone
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * created by yuanhang on 2022/2/21
 * description:
 */
class ArticleItemAdapter(private val context: Context) : RecyclerView.Adapter<ArticleItemAdapter.ArticleViewHolder>() {

    private val mArticles = ArrayList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(mArticles[position])
    }

    override fun getItemCount(): Int = mArticles.size

    fun setData(articles: List<Article>) {
        mArticles.clear()
        mArticles.addAll(articles)
        notifyDataSetChanged()
    }

    fun addData(articles: List<Article>) {
        val positionStart = mArticles.size
        mArticles.addAll(articles)
        notifyItemRangeInserted(positionStart, articles.size)
    }

    fun clear() {
        val originSize = mArticles.size
        mArticles.clear()
        notifyItemRangeRemoved(0, originSize)
    }

    inner class ArticleViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(articleItem: Article) {
            containerView.ivIsNew.isVisible = articleItem.fresh
            containerView.ivIsTop.isVisible = (articleItem.isTop == true)
            containerView.tvArticleTitle.text = Html.fromHtml(articleItem.title)
            if (articleItem.author.isBlank() && articleItem.shareUser.isBlank()) {
                containerView.gone()
            } else {
                containerView.tvAuthorOrSharer.text = if (articleItem.author.isBlank()) {
                    context.getString(R.string.home_page_article_sharer, articleItem.shareUser)
                } else {
                    context.getString(R.string.home_page_article_author, articleItem.author)
                }
            }
            if (articleItem.superChapterName.isBlank() && articleItem.chapterName.isBlank()) {
                containerView.tvKinds.gone()
            } else {
                containerView.tvKinds.text = "${articleItem.superChapterName}/${articleItem.chapterName}"
            }
            if (articleItem.niceShareDate.isBlank()) {
                containerView.tvTime.gone()
            }else {
                containerView.tvTime.text = articleItem.niceShareDate
            }
            if (articleItem.tags.isNotEmpty()) {
                val tagAdapter = ArticleTagItemAdapter(articleItem.tags)
                containerView.rvTags.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = tagAdapter
                }
            }
        }
    }
}