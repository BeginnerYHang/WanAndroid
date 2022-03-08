package com.yuanhang.wanandroid.ui.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.model.Project
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_project.*
import kotlinx.android.synthetic.main.item_project.view.*

/**
 * created by yuanhang on 2022/3/7
 * description:
 */
class ProjectItemAdapter(val context: BaseActivity) : RecyclerView.Adapter<ProjectItemAdapter.ProjectViewHolder>() {

    private val mProjectList = ArrayList<Project>()

    fun setData(newList: List<Project>) {
        mProjectList.clear()
        mProjectList.addAll(newList)
        notifyDataSetChanged()
    }

    fun addData(addList: List<Project>) {
        val originSize = mProjectList.size
        mProjectList.addAll(addList)
        notifyItemRangeInserted(originSize, addList.size)
    }

    fun clearData() {
        val originSize = mProjectList.size
        mProjectList.clear()
        notifyItemRangeRemoved(0, originSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(mProjectList[position])
    }

    override fun getItemCount(): Int = mProjectList.size


    inner class ProjectViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(project: Project) {
            context.mImageLoader.load(project.envelopePic, containerView.ivProjectCover)
            containerView.tvProjectTitle.text = project.title
            containerView.tvProjectDesc.text = project.desc
            containerView.tvProjectTime.text = project.niceDate
            containerView.tvProjectAuthor.text = project.getProjectAuthor()
            containerView.ivProjectCollect.setImageResource(
                if (project.zan == 0) R.drawable.ic_collect else R.drawable.ic_collected
            )
        }
    }
}