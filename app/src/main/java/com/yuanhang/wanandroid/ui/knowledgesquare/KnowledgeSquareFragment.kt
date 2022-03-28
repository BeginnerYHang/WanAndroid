package com.yuanhang.wanandroid.ui.knowledgesquare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.homepage.CommonArticleFragment
import com.yuanhang.wanandroid.ui.main.MainViewModel
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.fragment_knowledge_square.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class KnowledgeSquareFragment: BaseFragment() {

    private lateinit var mViewModel: KnowledgeSquareViewModel
    private var mArticleFragment: CommonArticleFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_knowledge_square, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (mArticleFragment == null) {
            mArticleFragment = CommonArticleFragment.newInstance(isShare = true)
            childFragmentManager.beginTransaction()
                .add(R.id.articleFragmentContainer, mArticleFragment!!).commit()
        }
    }
}