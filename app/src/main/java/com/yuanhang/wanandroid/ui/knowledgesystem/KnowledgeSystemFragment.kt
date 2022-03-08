package com.yuanhang.wanandroid.ui.knowledgesystem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.main.MainActivity
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.fragment_knowledge_system.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class KnowledgeSystemFragment : BaseFragment() {

    private lateinit var mViewModel: KnowledgeSystemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_knowledge_system, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, KnowledgeSystemViewModel::class.java)
        vpContainer.adapter = KnowledgeVpAdapter(childFragmentManager, lifecycle)
        vpContainer.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                        tvKnowledgeSystem.setTextColor(Color.BLACK)
                        tvNavigation.setTextColor(Color.GRAY)
                        tvUsefulWebsite.setTextColor(Color.GRAY)
                    }
                    1 -> {
                        tvKnowledgeSystem.setTextColor(Color.GRAY)
                        tvNavigation.setTextColor(Color.BLACK)
                        tvUsefulWebsite.setTextColor(Color.GRAY)
                    }
                    2 -> {
                        tvKnowledgeSystem.setTextColor(Color.GRAY)
                        tvNavigation.setTextColor(Color.GRAY)
                        tvUsefulWebsite.setTextColor(Color.BLACK)
                    }
                }
            }
        })
        vpContainer.currentItem = 0
        vpContainer.offscreenPageLimit = (vpContainer.adapter as KnowledgeVpAdapter).itemCount
        tvKnowledgeSystem.onClick {
            vpContainer.currentItem = 0
        }
        tvNavigation.onClick {
            vpContainer.currentItem = 1
        }
        tvUsefulWebsite.onClick {
            vpContainer.currentItem = 2
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("???", "onStart: KnowledgeSystemFragment")
    }

    override fun onResume() {
        super.onResume()
        Log.d("???", "onResume: KnowledgeSystemFragment")
    }
}