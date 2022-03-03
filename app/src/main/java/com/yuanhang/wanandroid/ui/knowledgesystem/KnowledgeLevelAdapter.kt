package com.yuanhang.wanandroid.ui.knowledgesystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.model.Level
import com.yuanhang.wanandroid.ui.main.MainActivity
import com.yuanhang.wanandroid.ui.search.HottestWordAdapter
import com.yuanhang.wanandroid.ui.search.HottestWordDecoration

/**
 * created by yuanhang on 2022/3/2
 * description:
 */
class KnowledgeLevelAdapter(val context: BaseActivity) :
    RecyclerView.Adapter<KnowledgeLevelAdapter.KnowledgeLevelViewHolder>() {

    private val knowledgeLevelList = mutableListOf<Level>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeLevelViewHolder {
        return KnowledgeLevelViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_knowledge_level, parent, false),
        )
    }

    override fun onBindViewHolder(holder: KnowledgeLevelViewHolder, position: Int) {
        holder.bind(knowledgeLevelList[position])
    }

    override fun getItemCount(): Int = knowledgeLevelList.size

    fun setData(newList: List<Level>) {
        knowledgeLevelList.clear()
        knowledgeLevelList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class KnowledgeLevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPrimaryLevel: TextView = itemView.findViewById(R.id.tvPrimaryLevel)
        val rvSubLevel: RecyclerView = itemView.findViewById(R.id.rvSubLevel)

        fun bind(knowledgeLevel: Level) {
            tvPrimaryLevel.text = knowledgeLevel.name
            rvSubLevel.apply {
                val levelAdapter = HottestWordAdapter<Level>() { _, levelId ->
                    levelId?.let {
                        KnowLedgeSystemResultActivity.startActivity(context as MainActivity, it)
                    }
                }
                this.adapter = levelAdapter
                knowledgeLevel.children?.let {
                    levelAdapter.setData(it)
                }
                layoutManager = FlexboxLayoutManager(context)
                if (itemDecorationCount == 0) {
                    addItemDecoration(HottestWordDecoration())
                }
            }
        }
    }
}