package com.yuanhang.wanandroid.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.model.HottestWord

/**
 * created by yuanhang on 2022/2/22
 * description:
 */
class HottestWordAdapter : RecyclerView.Adapter<HottestWordAdapter.HottestWordViewHolder>() {

    private val mHottestWordList = ArrayList<HottestWord>()

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

        fun bind(hottestWord: HottestWord) {
            tvHottestWord.text = hottestWord.name
        }
    }

    fun setData(newData: List<HottestWord>) {
        mHottestWordList.clear()
        mHottestWordList.addAll(newData)
        notifyDataSetChanged()
    }
}