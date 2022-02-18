package com.yuanhang.wanandroid.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuanhang.wanandroid.R

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class UserInfoFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_user_info_fragment, container, false)
    }
}