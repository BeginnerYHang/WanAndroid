package com.yuanhang.wanandroid.ui.knowledgesystem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_child_system.*

/**
 * created by yuanhang on 2022/3/3
 * description:
 */
class ChildSystemFragment: BaseFragment() {
    private lateinit var mViewModel: KnowledgeSystemViewModel
    private lateinit var mKnowledgeLevelAdapter: KnowledgeLevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_child_system, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(requireParentFragment(), KnowledgeSystemViewModel::class.java)
        Log.d("???", "onViewCreated: $mViewModel")
        mKnowledgeLevelAdapter = KnowledgeLevelAdapter(requireActivity() as MainActivity)
        rvLevel.apply {
            adapter = mKnowledgeLevelAdapter
            layoutManager = LinearLayoutManager(context)
        }
        getKnowledgeLevel()
    }

    fun getKnowledgeLevel() {
        mViewModel.getKnowledgeSystemData().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        it.forEach {
                            if (it.children != null) {
                                it.children.forEach { level ->
                                    level.parentChapterName = it.name
                                }
                            }
                        }
                        mKnowledgeLevelAdapter.setData(it)
                    }
                }
                Status.ERROR -> {
                    toastInform(it.message ?: "")
                }
            }
        }
    }
}