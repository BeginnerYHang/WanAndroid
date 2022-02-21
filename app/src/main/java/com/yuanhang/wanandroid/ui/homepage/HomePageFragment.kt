package com.yuanhang.wanandroid.ui.homepage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.common.WebViewActivity
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.fragment_home_page.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class HomePageFragment : BaseFragment() {

    private lateinit var mViewModel: HomePageViewModel
    private lateinit var mBannerAdapter: BannerItemAdapter
    private lateinit var mArticleAdapter: ArticleItemAdapter
    // 刷新操作需要重新请求,上拉加载不再需要
    private var isRefresh: Boolean = true
    private var pageIndex: Int = 0
    private var mHandler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val nextIndex = (vpBanner.currentItem + 1) % 3
            vpBanner.setCurrentItem(nextIndex, nextIndex != 0)
            mHandler.postDelayed(this, BANNER_INTERVAL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, HomePageViewModel::class.java)
        mBannerAdapter = BannerItemAdapter(mImageLoader) {
            WebViewActivity.start(requireActivity(), it.url)
        }
        mArticleAdapter = ArticleItemAdapter(requireContext())
        vpBanner.apply {
            adapter = mBannerAdapter
            currentItem = 0
        }
        vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        firstDot.setBackgroundResource(R.drawable.banner_dot_selected)
                        secondDot.setBackgroundResource(R.drawable.banner_dot)
                        thirdDot.setBackgroundResource(R.drawable.banner_dot)
                    }
                    1 -> {
                        firstDot.setBackgroundResource(R.drawable.banner_dot)
                        secondDot.setBackgroundResource(R.drawable.banner_dot_selected)
                        thirdDot.setBackgroundResource(R.drawable.banner_dot)
                    }
                    2 -> {
                        firstDot.setBackgroundResource(R.drawable.banner_dot)
                        secondDot.setBackgroundResource(R.drawable.banner_dot)
                        thirdDot.setBackgroundResource(R.drawable.banner_dot_selected)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == RecyclerView.SCROLL_STATE_IDLE) {
                    mHandler.postDelayed(runnable, BANNER_INTERVAL)
                } else {
                    mHandler.removeCallbacks(runnable)
                }
            }
        })
        rvArticle.apply {
            layoutManager = LinearLayoutManager(this@HomePageFragment.requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mArticleAdapter
        }
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
        getHomePageBanner()
        getAllArticle()
    }

    fun getHomePageBanner() {
        mViewModel.getHomePageBanner().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.code == 0) {
                        it.data?.let {
                            llIndicator.visible()
                            mBannerAdapter.setData(it)
                            mHandler.postDelayed(runnable, BANNER_INTERVAL)
                        }
                    } else {
                        toastFail(it.message ?: "")
                    }
                }
                Status.ERROR -> {
                    toastInform(it.message ?: "")
                }
            }
        }
    }

    fun getAllArticle() {
        mViewModel.getAllArticle(isRefresh, pageIndex).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        if (isRefresh) {
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

    companion object {
        const val BANNER_INTERVAL = 5000L
    }
}