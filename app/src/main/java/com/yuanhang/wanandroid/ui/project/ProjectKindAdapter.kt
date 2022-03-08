package com.yuanhang.wanandroid.ui.project

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.model.Level
import com.yuanhang.wanandroid.util.onClick

/**
 * created by yuanhang on 2022/3/7
 * description:
 */
class ProjectKindAdapter(val kindClick: ((Int) -> Unit)? = null) : RecyclerView.Adapter<ProjectKindAdapter.ProjectKindViewHolder>() {

    private val kindList = ArrayList<Level>()
    private var clickPos = -1
    private var lastClickPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectKindViewHolder {
        return ProjectKindViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_project_kind, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProjectKindViewHolder, position: Int) {
        holder.bind(kindList[position])

    }

    override fun getItemCount(): Int = kindList.size

    fun setData(newList: List<Level>) {
        kindList.clear()
        kindList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setClickPosition(position: Int) {
        lastClickPos = clickPos
        clickPos = position
        if (lastClickPos != -1) {
            notifyItemChanged(lastClickPos)
        }
        notifyItemChanged(clickPos)
    }

    inner class ProjectKindViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvKindName = itemView.findViewById<TextView>(R.id.tvKindName)

        fun bind(projectKind: Level) {
            tvKindName.setTextColor(
                if (adapterPosition == clickPos) Color.parseColor("#FF03DAC5") else Color.GRAY
            )
            tvKindName.text = Html.fromHtml(projectKind.name)
            itemView.onClick {
                kindClick?.invoke(kindList[adapterPosition].id)
                setClickPosition(adapterPosition)
            }
        }
    }
}