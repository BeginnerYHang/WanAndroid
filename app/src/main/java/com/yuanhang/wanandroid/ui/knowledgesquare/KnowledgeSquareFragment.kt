package com.yuanhang.wanandroid.ui.knowledgesquare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseFragment

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class KnowledgeSquareFragment: BaseFragment() {

    private lateinit var mViewModel: KnowledgeSquareViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_knowledge_square, container, false)
    }
}