package com.yuanhang.wanandroid.ui.homepage

import android.content.Intent
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.model.Level
import com.yuanhang.wanandroid.ui.common.NicknameClickSpan
import com.yuanhang.wanandroid.ui.knowledgesystem.KnowLedgeSystemResultActivity
import com.yuanhang.wanandroid.ui.my.UserInfoActivity
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * created by yuanhang on 2022/2/21
 * description:
 */
class ArticleItemAdapter(private val context: BaseActivity,
                         val isShare: Boolean = false,
                         val collectArticleClick: ((Article, Int) -> Unit)) : RecyclerView.Adapter<ArticleItemAdapter.ArticleViewHolder>() {

    private val mArticles = ArrayList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        val holder = ArticleViewHolder(itemView)
        holder.containerView.ivArticleCollect.onClick {
            val position = holder.adapterPosition
            collectArticleClick.invoke(mArticles[position], position)
        }
        return holder
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
        mArticles.clear()
        notifyDataSetChanged()
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
                val spanStart: Int
                val spanEnd: Int
                val authorTip = if (articleItem.author.isBlank()) {
                    spanStart = 4
                    spanEnd = spanStart + articleItem.shareUser.length
                    context.getString(R.string.home_page_article_sharer, articleItem.shareUser)
                } else {
                    spanStart = 3
                    spanEnd = spanStart + articleItem.author.length
                    context.getString(R.string.home_page_article_author, articleItem.author)
                }
                val spannable = SpannableString(authorTip)
                if (articleItem.userId > 0) {
                    spannable.setSpan(NicknameClickSpan(){
                        UserInfoActivity.startActivity(context, articleItem.userId)
                    }, spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    containerView.tvAuthorOrSharer.setMovementMethodDefault()
                }
                containerView.tvAuthorOrSharer.text = spannable
            }
            if (isShare && articleItem.superChapterName.isBlank() && articleItem.chapterName.isBlank()) {
                containerView.tvKinds.gone()
            } else {
                val spannable = SpannableString(context.getString(R.string.article_kind_hint, "${articleItem.superChapterName}/${articleItem.chapterName}"))
                spannable.setSpan(NicknameClickSpan {
                    if (context !is KnowLedgeSystemResultActivity) {
                        KnowLedgeSystemResultActivity.startActivity(context, Level(articleItem.superChapterId,
                            articleItem.chapterName,articleItem.realSuperChapterId, parentChapterName = articleItem.superChapterName ))
                    }
                },3,3 + articleItem.superChapterName.length + articleItem.chapterName.length + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                containerView.tvKinds.setMovementMethodDefault()
                containerView.tvKinds.text = spannable
            }
            if (articleItem.niceShareDate.isBlank()) {
                containerView.tvTime.gone()
            }else {
                containerView.tvTime.text = articleItem.niceShareDate
            }
            if (articleItem.tags.isNotEmpty()) {
                val tagAdapter = ArticleTagItemAdapter(articleItem.tags)
                containerView.rvTags.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
                    adapter = tagAdapter
                }
            } else {
                containerView.rvTags.apply {
                    adapter = null
                }
            }
            if (articleItem.collect) {
                containerView.ivArticleCollect.setImageResource(R.drawable.ic_collected)
            } else {
                containerView.ivArticleCollect.setImageResource(R.drawable.ic_collect)
            }
        }
    }
}