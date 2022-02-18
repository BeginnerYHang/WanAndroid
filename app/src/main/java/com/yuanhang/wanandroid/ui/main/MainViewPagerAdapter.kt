package com.yuanhang.wanandroid.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuanhang.wanandroid.ui.homepage.HomePageFragment
import com.yuanhang.wanandroid.ui.knowledgesquare.KnowledgeSquareFragment
import com.yuanhang.wanandroid.ui.knowledgesystem.KnowledgeSystemFragment
import com.yuanhang.wanandroid.ui.my.UserInfoFragment
import com.yuanhang.wanandroid.ui.project.ProjectFragment

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragments by lazy {
        mutableListOf(
            HomePageFragment(),
            KnowledgeSquareFragment(),
            KnowledgeSystemFragment(),
            ProjectFragment(),
            UserInfoFragment()
        )
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}