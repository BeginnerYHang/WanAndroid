package com.yuanhang.wanandroid.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.model.HottestWord
import com.yuanhang.wanandroid.model.Level
import com.yuanhang.wanandroid.model.UsefulWebsite
import com.yuanhang.wanandroid.util.onClick

/**
 * created by yuanhang on 2022/2/22
 * description:
 */
class HottestWordAdapter<T>(val itemViewClick: ((String?, T?) -> Unit)? = null) : RecyclerView.Adapter<HottestWordAdapter<T>.HottestWordViewHolder>() {

    private val mHottestWordList = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HottestWordViewHolder {
        return HottestWordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_hottest_word, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HottestWordViewHolder, position: Int) {
        holder.bind(mHottestWordList[position])
    }

    override fun getItemCount(): Int = mHottestWordList.size


    inner class HottestWordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHottestWord: TextView = itemView.findViewById(R.id.tvHottestWord)

        fun bind(t: T) {
            when(t) {
                is HottestWord -> {
                    tvHottestWord.text = t.name
                    itemView.onClick {
                        itemViewClick?.invoke(t.name, null)
                    }
                }
                is Level -> {
                    tvHottestWord.text = t.name
                    itemView.onClick {
                        itemViewClick?.invoke(null, t)
                    }
                }
                is UsefulWebsite -> {
                    tvHottestWord.text = t.name
                    itemView.onClick {
                        itemViewClick?.invoke(null, t)
                    }
                }
                is Article -> {
                    tvHottestWord.text = t.title
                    itemView.onClick {
                        itemViewClick?.invoke(null, t)
                    }
                }
            }
        }
    }

    fun setData(newData: List<T>) {
        mHottestWordList.clear()
        mHottestWordList.addAll(newData)
        notifyDataSetChanged()
    }
}