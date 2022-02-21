package com.yuanhang.wanandroid.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.WanAndroidApplication
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.common.CommonTipsDialog
import com.yuanhang.wanandroid.ui.login.LoginActivity
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class UserInfoFragment: BaseFragment() {

    lateinit var mViewModel: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, UserInfoViewModel::class.java)
        llLogout.onClick {
            CommonTipsDialog(
                requireActivity() as BaseActivity,
                getString(R.string.dialog_confirm),
                getString(R.string.logout_dialog_message),
                getString(R.string.dialog_confirm),
                {logout()},
                getString(R.string.dialog_cancel),
            ).show()
        }
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
                    LoginActivity.start(requireActivity() as BaseActivity)
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

}