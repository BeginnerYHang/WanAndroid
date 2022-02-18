package com.yuanhang.wanandroid.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.WanAndroidApplication
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.ui.common.CommonTipsDialog
import com.yuanhang.wanandroid.ui.login.LoginActivity
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_layout_tab.view.*


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
            R.drawable.icon_home_page,
            R.drawable.icon_knowledge_system,
            R.drawable.icon_knowledge_square,
            R.drawable.icon_project,
            R.drawable.icon_user_info
        )
        val tabTextList = mutableListOf(
            "首页", "体系", "广场", "项目", "我的"
        )
        TabLayoutMediator(
            tlMain, vpMain, true, false
        ) { tab, position ->
            val tabCustomView = LayoutInflater.from(this).inflate(R.layout.item_layout_tab, null)
            tabCustomView.tvTab.text = tabTextList[position]
            tabCustomView.ivTab.setImageResource(tabIconList[position])
            tab.customView = tabCustomView
        }.attach()
//        llLogout.onClick {
//            CommonTipsDialog(
//                this,
//                getString(R.string.dialog_confirm),
//                getString(R.string.logout_dialog_message),
//                getString(R.string.dialog_confirm),
//                {logout()},
//                getString(R.string.dialog_cancel),
//            ).show()
//        }
    }

//fun logout() {
//    mViewModel.logout().observe(this) {
//        when (it.status) {
//            Status.SUCCESS -> {
//                if (it.code == 0) {
//                    //成功退出
//                    for (activity in WanAndroidApplication.app.getCurrentOpenedActivities()) {
//                        if (!activity.isFinishing) {
//                            activity.finish()
//                        }
//                    }
//                    LoginActivity.start(this)
//                } else {
//                    toastInform(it.message ?: "")
//                }
//            }
//            Status.ERROR -> {
//                toastFail(it.message ?: "")
//            }
//        }
//    }
//}

    companion object {
        fun start(activity: BaseActivity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }
}