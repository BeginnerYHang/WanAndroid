package com.yuanhang.wanandroid.ui.my

import android.graphics.Rect
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.WanAndroidApplication
import com.yuanhang.wanandroid.api.COOKIE_HEADER
import com.yuanhang.wanandroid.api.CommonInfoStore
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.model.CoinInfo
import com.yuanhang.wanandroid.ui.common.CommonTipsDialog
import com.yuanhang.wanandroid.ui.common.FormTipsDialog
import com.yuanhang.wanandroid.ui.common.NicknameClickSpan
import com.yuanhang.wanandroid.ui.homepage.ArticleItemAdapter
import com.yuanhang.wanandroid.ui.login.LoginActivity
import com.yuanhang.wanandroid.util.*
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class UserInfoFragment : BaseFragment() {

    private lateinit var mViewModel: UserInfoViewModel
    private lateinit var mAdapter: ArticleItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, UserInfoViewModel::class.java)
        mViewModel.userId = arguments?.get(USER_ID) as? Int?: CommonInfoStore.getUserId()
        mViewModel.isGuest = CommonInfoStore.getUserId() != mViewModel.userId
        tvCollection.text = if (mViewModel.isGuest) getString(R.string.his_collection_article)
                            else getString(R.string.my_collection_article)
        tvCollection.onClick {
            //TODO:跳转到我的收藏页面
        }
        llLogout.onClick {
            CommonTipsDialog(
                requireActivity() as BaseActivity,
                getString(R.string.dialog_confirm),
                getString(R.string.logout_dialog_message),
                getString(R.string.dialog_confirm),
                { logout() },
                getString(R.string.dialog_cancel),
            ).show()
        }
        mAdapter = ArticleItemAdapter(requireActivity() as BaseActivity) { article, position ->

        }
        rvShareArticle.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = dp2px(parent.context, 6)
                    outRect.left = dp2px(parent.context, 8)
                    outRect.right = dp2px(parent.context, 8)
                }
            })
        }
        val shareEmptyInfo = getString(R.string.myself_share_list_empty_info)
        val shareEmptySpannable = SpannableString(shareEmptyInfo)
        val startPosition = shareEmptyInfo.lastIndexOf("分享")
        shareEmptySpannable.setSpan(NicknameClickSpan(){
            showFormTipsDialog()
        } ,startPosition, startPosition + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        userInfoShareEmptyHint.setMovementMethodDefault()
        userInfoShareEmptyHint.text = shareEmptySpannable
        userInfoGroup.isVisible = !mViewModel.isGuest
        getShareArticleList(true)
        shareRefreshLayout.setOnRefreshListener {
            getShareArticleList(true)
        }
        shareRefreshLayout.setOnLoadMoreListener {
            getShareArticleList(false)
        }
    }

    fun getShareArticleList(isRefresh: Boolean) {
        mViewModel.userId?.let {
            mViewModel.getShareArticle(it, mViewModel.isGuest).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            shareRefreshLayout.setNoMoreData(it.shareArticles.offset + it.shareArticles.articles.size == it.shareArticles.total)
                        }
                        if (isRefresh) {
                            mViewModel.pageIndex = 1
                            shareRefreshLayout.finishRefresh()
                            it.data?.coinInfo?.let {
                                setUpCoinInfoView(it)
                            }
                            it.data?.shareArticles?.articles?.let {
                                if (it.isEmpty()) {
                                    if (mViewModel.isGuest) {
                                        shareRefreshLayout.gone()
                                        tvRecentlyShare.gone()
                                    } else {
                                        userInfoShareEmptyHint.visible()
                                    }
                                }
                                mAdapter.setData(it)
                            }
                        } else {
                            mViewModel.pageIndex += 1
                            shareRefreshLayout.finishLoadMore()
                            it.data?.shareArticles?.articles?.let {
                                mAdapter.addData(it)
                            }
                        }
                    }
                    Status.ERROR -> {
                        if (isRefresh) {
                            shareRefreshLayout.finishRefresh(false)
                        } else {
                            shareRefreshLayout.finishLoadMore(false)
                        }
                    }
                }
            }
        }
    }

    fun setUpCoinInfoView(coinInfo: CoinInfo) {
        nickname.text = coinInfo.nickname
        val coinSpannable = SpannableString(getString(R.string.coin_info, coinInfo.coinCount.toString()))
        val spannableStartPosition = coinSpannable.indexOfFirst {
            it == '('
        }
        coinSpannable.setSpan(NicknameClickSpan(){
            //Todo:跳转到积分明细页面
        }, spannableStartPosition + 1, spannableStartPosition + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvCoinCount.setMovementMethodDefault()
        tvCoinCount.text = coinSpannable
        tvLevel.text = getString(R.string.level_info, coinInfo.level.toString())
        tvRank.text = getString(R.string.rank_info, coinInfo.rank)
    }

    fun logout() {
        mViewModel.logout().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.code == 0) {
                        //成功退出
                        for (activity in WanAndroidApplication.app.getCurrentOpenedActivities()) {
                            if (!activity.isFinishing) {
                                activity.finish()
                            }
                        }
                        SPUtils.remove(requireContext(), COOKIE_HEADER)
                        LoginActivity.start(requireActivity() as BaseActivity, "MainActivity")
                    } else {
                        toastInform(it.message ?: "")
                    }
                }
                Status.ERROR -> {
                    toastFail(it.message ?: "")
                }
            }
        }
    }

    private fun showFormTipsDialog() {
        FormTipsDialog(requireContext(), { title, link ->
            mViewModel.shareArticle(title, link).observe(viewLifecycleOwner) {
                when(it.status) {
                    Status.SUCCESS -> {
                        toastSuccess(getString(R.string.share_article_success))
                    }
                    Status.ERROR -> {
                        toastInform(it.message?: "")
                    }
                }
            }
        }).show()
    }

    companion object {

        const val USER_ID = "userId"

        fun newInstance(userId: Int): UserInfoFragment {
            return UserInfoFragment().apply {
                arguments = bundleOf(USER_ID to userId)
            }
        }
    }
}