package com.yuanhang.wanandroid.ui.homepage

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.main.MainActivity
import com.yuanhang.wanandroid.util.dp2px
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.fragment_common_article.*

/**
 * created by yuanhang on 2022/3/2
 * description: 由于搜索列表和首页文章列表相同,因此封装为统一的Fragment
 */
class CommonArticleFragment: BaseFragment() {

    private lateinit var mViewModel: CommonArticleViewModel
    private lateinit var mArticleAdapter: ArticleItemAdapter
    private var keyWord: String? = null
    private var levelId: Int? = null
    // 刷新操作需要重新请求,上拉加载不再需要
    private var isRefresh: Boolean = true
    private var pageIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_common_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, CommonArticleViewModel::class.java)
        mArticleAdapter = ArticleItemAdapter(requireActivity() as MainActivity)
        rvArticle.apply {
            layoutManager = LinearLayoutManager(this@CommonArticleFragment.requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mArticleAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = dp2px(parent.context, 6)
                    outRect.left = dp2px(parent.context, 4)
                    outRect.right = dp2px(parent.context, 4)
                }
            })
        }
        keyWord = arguments?.get(KEYWORD) as? String
        levelId = arguments?.get(LEVELID) as? Int
        smartRefreshLayout.setOnRefreshListener {
            isRefresh = true
            pageIndex = 0
            getAllArticle()
        }
        smartRefreshLayout.setOnLoadMoreListener {
            pageIndex += 1
            isRefresh = false
            getAllArticle()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllArticle()
    }

    fun setKeyWord(keyWord: String) {
        this.keyWord = keyWord
        isRefresh = true
        pageIndex = 0
        mArticleAdapter.clear()
        tvEmptyResult.gone()
        getAllArticle()
    }

    fun getAllArticle() {
        mViewModel.getAllArticle(isRefresh, pageIndex, keyWord, levelId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        if (isRefresh) {
                            if (it.articles.isEmpty()) {
                                tvEmptyResult.visible()
                            } else {
                                tvEmptyResult.gone()
                            }
                            mArticleAdapter.setData(it.articles)
                            smartRefreshLayout.finishRefresh(true)
                        } else {
                            mArticleAdapter.addData(it.articles)
                            if (pageIndex + 1 >= it.pageCount) {
                                smartRefreshLayout.finishLoadMoreWithNoMoreData()
                            }else {
                                smartRefreshLayout.finishLoadMore(true)
                            }

                        }
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    toastInform(it.message ?: "")
                    if (isRefresh) {
                        smartRefreshLayout.finishRefresh(false)
                    } else {
                        smartRefreshLayout.finishLoadMore(false)
                    }
                }
            }
        }
    }

    companion object{

        const val KEYWORD = "keyWord"
        const val LEVELID = "levelId"

        fun newInstance(keyWord: String? = null, levelId: Int? = null): CommonArticleFragment {
            return CommonArticleFragment().apply {
                arguments = bundleOf(KEYWORD to keyWord, LEVELID to levelId)
            }
        }
    }
}