package com.yuanhang.wanandroid.ui.knowledgesystem

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * created by yuanhang on 2022/3/4
 * description:
 */
class KnowledgeVpAdapter(fm: FragmentManager,  lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle) {

    private val mFragments by lazy {
        mutableListOf(ChildSystemFragment(), NavigationFragment(), UsefulWebsiteFragment())
    }

    override fun getItemCount(): Int = mFragments.size

    override fun createFragment(position: Int): Fragment = mFragments[position]
}