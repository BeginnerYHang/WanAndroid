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
import kotlinx.android.synthetic.main.fragment_knowledge_system.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class KnowledgeSystemFragment : BaseFragment() {

    private lateinit var mViewModel: KnowledgeSystemViewModel
    private lateinit var mKnowledgeLevelAdapter: KnowledgeLevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_knowledge_system, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, KnowledgeSystemViewModel::class.java)
        mKnowledgeLevelAdapter = KnowledgeLevelAdapter(requireActivity() as MainActivity)
        rvLevel.apply {
            adapter = mKnowledgeLevelAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()
        getKnowledgeLevel()
    }

    fun getKnowledgeLevel() {
        mViewModel.getKnowledgeSystemData().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
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