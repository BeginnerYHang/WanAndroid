package com.yuanhang.wanandroid.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class ProjectFragment : BaseFragment() {

    private lateinit var mViewModel: ProjectViewModel
    private lateinit var mKindAdapter: ProjectKindAdapter
    private var mProjectListFragment: ProjectListFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, ProjectViewModel::class.java)
        mKindAdapter = ProjectKindAdapter() { kindId ->
            if (mProjectListFragment == null) {
                mProjectListFragment = ProjectListFragment.newInstance(kindId)
                childFragmentManager.beginTransaction().add(R.id.projectFragmentContainer, mProjectListFragment!!).commit()
            } else {
                mProjectListFragment!!.setKindId(kindId)
            }
        }
        rvProjectNavigation.apply {
            adapter = mKindAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        getProjectKindList()
    }

    fun getProjectKindList() {
        mViewModel.getProjectKinds().observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        mKindAdapter.setData(it)
                        if (it.isNotEmpty()) {
                            mKindAdapter.setClickPosition(0)
                            if (mProjectListFragment == null) {
                                mProjectListFragment = ProjectListFragment.newInstance(it[0].id)
                                childFragmentManager.beginTransaction().add(R.id.projectFragmentContainer, mProjectListFragment!!).commit()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    toastInform(it.message?: "")
                }
            }
        }
    }
}