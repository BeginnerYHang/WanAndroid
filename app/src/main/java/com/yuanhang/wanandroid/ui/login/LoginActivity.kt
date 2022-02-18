package com.yuanhang.wanandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.ViewModelProvider
import com.yuanhang.wanandroid.ui.main.MainActivity
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.CommonInfoStore
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.onClick
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(LoginViewModel::class.java)
        btnLogin.onClick {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                mViewModel.login(username, password).observe(this) {
                    when(it.status) {
                        Status.SUCCESS -> {
                            pbLogin.gone()
                            if (it.data == null) {
                                //说明账户名和密码不匹配
                                toastFail(it.message ?: "")
                            } else {
                                //登录成功
                                CommonInfoStore.loginInfo = it.data
                                MainActivity.start(this)
                                finish()
                            }
                        }
                        Status.LOADING -> {
                            pbLogin.visible()
                        }
                        Status.ERROR -> {
                            pbLogin.gone()
                            toastInform(it.message ?: "")
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun start(activity: BaseActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }
}