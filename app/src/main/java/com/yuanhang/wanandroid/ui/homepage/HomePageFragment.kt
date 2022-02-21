package com.yuanhang.wanandroid.ui.homepage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.common.WebViewActivity
import kotlinx.android.synthetic.main.layout_home_page_fragment.*

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_home_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, HomePageViewModel::class.java)
        mBannerAdapter = BannerItemAdapter(mImageLoader) {
            WebViewActivity.start(this.requireActivity(), it.url)
        }
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
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getHomePageBanner().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.code == 0) {
                        it.data?.let {
                            mBannerAdapter.submitData(it)
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

    companion object {
        const val BANNER_INTERVAL = 5000L
    }
}