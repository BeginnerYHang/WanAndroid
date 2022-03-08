package com.yuanhang.wanandroid.ui.knowledgesystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.model.UsefulWebsite
import com.yuanhang.wanandroid.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_useful_website.*

/**
 * created by yuanhang on 2022/3/3
 * description:
 */
class UsefulWebsiteFragment: BaseFragment() {
    private lateinit var mViewModel: KnowledgeSystemViewModel
    private lateinit var mAdapter: UsefulWebsiteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_useful_website, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(requireParentFragment(), KnowledgeSystemViewModel::class.java)
        mAdapter = UsefulWebsiteAdapter(requireActivity() as MainActivity)
        rvUsefulWebsite.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        getUsefulWebSite()
    }

    fun getUsefulWebSite() {
        mViewModel.getUsefulWebsiteData().observe(this) {
            when(it.status) {
                Status.SUCCESS -> {
                    val map = mutableMapOf<String, List<UsefulWebsite>>()
                    it.data?.let {
                        it.forEach {
                            if (map.containsKey(it.category)) {
                                (map.getValue(it.category) as ArrayList).add(it)
                            } else {
                                map[it.category] = ArrayList<UsefulWebsite>().apply {
                                    add(it)
                                }
                            }
                        }
                        mAdapter.setData(map.entries.toList())
                    }
                }
            }
        }
    }
}