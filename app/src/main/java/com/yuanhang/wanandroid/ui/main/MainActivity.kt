package com.yuanhang.wanandroid.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.tabs.TabLayoutMediator
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.ui.search.SearchActivity
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_tab.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class MainActivity : BaseActivity() {

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = getViewModel(this, MainViewModel::class.java)
        val mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)
        vpMain.apply {
            adapter = mainViewPagerAdapter
            offscreenPageLimit = mainViewPagerAdapter.itemCount
            isUserInputEnabled = false
        }
        val tabIconList = mutableListOf(
            R.drawable.tab_home_page,
            R.drawable.tab_knowledge_system,
            R.drawable.tab_knowledge_square,
            R.drawable.tab_project,
            R.drawable.tab_user_info
        )
        val tabTextList = mutableListOf(
            "首页", "体系", "广场", "项目", "我的"
        )
        TabLayoutMediator(
            tlMain, vpMain, true, false
        ) { tab, position ->
            val tabCustomView = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
            tabCustomView.tvTab.text = tabTextList[position]
            tabCustomView.ivTab.setImageResource(tabIconList[position])
            tab.customView = tabCustomView
        }.attach()
    }

    companion object {
        fun start(activity: BaseActivity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }
}