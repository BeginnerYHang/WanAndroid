package com.yuanhang.wanandroid.ui.knowledgesystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.model.NavigationItem
import com.yuanhang.wanandroid.model.UsefulWebsite
import com.yuanhang.wanandroid.ui.common.WebViewActivity
import com.yuanhang.wanandroid.ui.main.MainActivity
import com.yuanhang.wanandroid.ui.search.HottestWordAdapter
import com.yuanhang.wanandroid.ui.search.HottestWordDecoration

/**
 * created by yuanhang on 2022/3/5
 * description:
 */
class NavigationAdapter(val con: MainActivity): RecyclerView.Adapter<NavigationAdapter.NavigationItemViewHolder>() {

    private val navigationItems = mutableListOf<NavigationItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        return NavigationItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_knowledge_level, parent, false),
        )
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.bind(navigationItems[position])
    }

    override fun getItemCount(): Int = navigationItems.size

    fun setData(newList: List<NavigationItem>) {
        navigationItems.clear()
        navigationItems.addAll(newList)
        notifyDataSetChanged()
    }

    inner class NavigationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPrimaryLevel: TextView = itemView.findViewById(R.id.tvPrimaryLevel)
        val rvSubLevel: RecyclerView = itemView.findViewById(R.id.rvSubLevel)

        fun bind(navigationItem: NavigationItem) {
            tvPrimaryLevel.text = navigationItem.name
            rvSubLevel.apply {
                val itemAdapter = HottestWordAdapter<Article>() { _, article ->
                    article?.let {
                        WebViewActivity.start(con, it.link)
                    }
                }
                this.adapter = itemAdapter
                itemAdapter.setData(navigationItem.articles)
                layoutManager = FlexboxLayoutManager(context)
                if (itemDecorationCount == 0) {
                    addItemDecoration(HottestWordDecoration())
                }
            }
        }
    }
}