package com.yuanhang.wanandroid.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(LoginViewModel::class.java)
        btnLogin.onClick {
            mViewModel.login("yuanhang", "123").observe(this) {
                when(it.status) {
                    Status.SUCCESS -> {
                        if (it.data == null) {

                        }
                    }
                }
            }
        }
    }
}