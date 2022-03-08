package com.yuanhang.wanandroid.ui.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.fragment_project_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [ProjectListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectListFragment : BaseFragment() {

    private lateinit var mProjectItemAdapter: ProjectItemAdapter
    private lateinit var mViewModel: ProjectListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, ProjectListViewModel::class.java)
        mProjectItemAdapter = ProjectItemAdapter(requireActivity() as BaseActivity)
        mViewModel.kindId = arguments?.getInt(KIND_ID)?: 0
        rvProjects.apply {
            adapter = mProjectItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        projectRefreshLayout.setOnRefreshListener {
            getProjectList(true)
        }
        projectRefreshLayout.setOnLoadMoreListener {
            getProjectList(false)
        }
        getProjectList(true)
    }

    fun getProjectList(isRefresh: Boolean) {
        if (isRefresh) {
            mViewModel.pageIndex = 1
        } else {
            mViewModel.pageIndex += 1
        }
        mViewModel.getProjectList().observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        if(isRefresh) {
                            mProjectItemAdapter.setData(it.articles)
                            projectRefreshLayout.finishRefresh()
                        } else {
                            mProjectItemAdapter.addData(it.articles)
                            if (it.offset + it.articles.size == it.total) {
                                projectRefreshLayout.finishLoadMoreWithNoMoreData()
                            } else {
                                projectRefreshLayout.finishLoadMore()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    if (isRefresh) {
                        projectRefreshLayout.finishRefresh(false)
                    } else {
                        projectRefreshLayout.finishLoadMore(false)
                    }
                    toastInform(it.message?: "")
                }
            }
        }
    }

    fun setKindId(newKindId: Int) {
        mProjectItemAdapter.clearData()
        mViewModel.kindId = newKindId
        mViewModel.pageIndex = 1
        getProjectList(true)
    }

    companion object {

        const val KIND_ID = "kindId"

        fun newInstance(kindId: Int) =
            ProjectListFragment().apply {
                arguments = bundleOf(KIND_ID to kindId)
            }
    }
}