package com.yuanhang.wanandroid.ui.homepage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.common.WebViewActivity
import com.yuanhang.wanandroid.ui.search.SearchActivity
import com.yuanhang.wanandroid.util.onClick
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class HomePageFragment : BaseFragment() {

    private lateinit var mViewModel: HomePageViewModel
    private lateinit var mBannerAdapter: BannerItemAdapter
    private var mHandler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val nextIndex = (vpBanner.currentItem + 1) % 3
            vpBanner.setCurrentItem(nextIndex, nextIndex != 0)
            mHandler.postDelayed(this, BANNER_INTERVAL)
        }
    }
    private var isInit = false

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
        childFragmentManager.beginTransaction()
            .add(R.id.articleFragmentContainer, CommonArticleFragment.newInstance())
            .commit()
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
                        toolBar.setRightIcon(R.drawable.ic_search)
                    }
                    1 -> {
                        firstDot.setBackgroundResource(R.drawable.banner_dot)
                        secondDot.setBackgroundResource(R.drawable.banner_dot_selected)
                        thirdDot.setBackgroundResource(R.drawable.banner_dot)
                        toolBar.setRightIcon(R.drawable.ic_search_white)
                    }
                    2 -> {
                        firstDot.setBackgroundResource(R.drawable.banner_dot)
                        secondDot.setBackgroundResource(R.drawable.banner_dot)
                        thirdDot.setBackgroundResource(R.drawable.banner_dot_selected)
                        toolBar.setRightIcon(R.drawable.ic_search)
                    }
                }
            }
        })
        rightIcon.onClick {
            SearchActivity.start(requireActivity() as BaseActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        getHomePageBanner()
    }

    fun getHomePageBanner() {
        mViewModel.getHomePageBanner().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.code == 0) {
                        it.data?.let {
                            llIndicator.visible()
                            mBannerAdapter.setData(it)
                            if (!isInit) {
                                isInit = true
                                mHandler.postDelayed(runnable, BANNER_INTERVAL)
                            }
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

    companion object {
        const val BANNER_INTERVAL = 5000L
    }
}