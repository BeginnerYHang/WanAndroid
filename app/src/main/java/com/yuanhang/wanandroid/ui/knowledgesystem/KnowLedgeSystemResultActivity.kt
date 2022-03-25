package com.yuanhang.wanandroid.ui.knowledgesystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.model.Level
import com.yuanhang.wanandroid.ui.homepage.CommonArticleFragment
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.activity_knowledge_system_result.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlin.math.log

class KnowLedgeSystemResultActivity : BaseActivity() {

    private lateinit var mViewModel: KnowLedgeSystemResultViewModel
    private var level: Level? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_system_result)
        mViewModel = getViewModel(this, KnowLedgeSystemResultViewModel::class.java)
        level = intent.getParcelableExtra(LEVEL)
        initView()
    }

    fun initView() {
        leftIcon.onClick {
            onBackPressed()
        }
        if (level?.name != null && level?.parentChapterName != null) {
            toolBar.setMediumText("${level?.parentChapterName}/${level?.name}")
        }
        level?.id?.let {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CommonArticleFragment.newInstance(levelId = it))
                .commit()
        }
    }


    companion object {
        const val LEVEL = "level"

        fun startActivity(from: BaseActivity,
                          level: Level) {
            val intent = Intent(from, KnowLedgeSystemResultActivity::class.java)
            intent.putExtra(LEVEL, level)
            from.startActivity(intent)
        }
    }
}