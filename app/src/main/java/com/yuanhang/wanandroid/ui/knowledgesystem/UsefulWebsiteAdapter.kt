package com.yuanhang.wanandroid.ui.knowledgesystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.model.UsefulWebsite
import com.yuanhang.wanandroid.ui.common.WebViewActivity
import com.yuanhang.wanandroid.ui.main.MainActivity
import com.yuanhang.wanandroid.ui.search.HottestWordAdapter
import com.yuanhang.wanandroid.ui.search.HottestWordDecoration

/**
 * created by yuanhang on 2022/3/4
 * description:
 */
class UsefulWebsiteAdapter(val con: MainActivity): RecyclerView.Adapter<UsefulWebsiteAdapter.UsefulWebSiteViewHolder>() {

    private val usefulWebSiteList = mutableListOf<MutableMap.MutableEntry<String, List<UsefulWebsite>>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsefulWebSiteViewHolder {
        return UsefulWebSiteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_knowledge_level, parent, false),
        )
    }

    override fun onBindViewHolder(holder: UsefulWebSiteViewHolder, position: Int) {
        holder.bind(usefulWebSiteList[position])
    }

    override fun getItemCount(): Int = usefulWebSiteList.size

    fun setData(newList: List<MutableMap.MutableEntry<String, List<UsefulWebsite>>>) {
        usefulWebSiteList.clear()
        usefulWebSiteList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class UsefulWebSiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPrimaryLevel: TextView = itemView.findViewById(R.id.tvPrimaryLevel)
        val rvSubLevel: RecyclerView = itemView.findViewById(R.id.rvSubLevel)

        fun bind(entry: MutableMap.MutableEntry<String, List<UsefulWebsite>>) {
            tvPrimaryLevel.text = entry.key
            rvSubLevel.apply {
                val usefulWebsiteAdapter = HottestWordAdapter<UsefulWebsite>() { _, webSite ->
                    webSite?.let {
                        WebViewActivity.start(con, it.link)
                    }
                }
                this.adapter = usefulWebsiteAdapter
                usefulWebsiteAdapter.setData(entry.value)
                layoutManager = FlexboxLayoutManager(context)
                if (itemDecorationCount == 0) {
                    addItemDecoration(HottestWordDecoration())
                }
            }
        }
    }
}