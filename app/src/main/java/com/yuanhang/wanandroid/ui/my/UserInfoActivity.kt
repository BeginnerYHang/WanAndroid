package com.yuanhang.wanandroid.ui.my

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity


class UserInfoActivity : AppCompatActivity() {

    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        userId = intent.getIntExtra(UserInfoFragment.USER_ID, 0)
        userId?.let {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UserInfoFragment.newInstance(it))
        }
    }


    companion object {
        fun startActivity(from: BaseActivity, userId: Int) {
            val intent = Intent(from, UserInfoActivity::class.java)
            intent.putExtra(UserInfoFragment.USER_ID, userId)
            from.startActivity(intent)
        }
    }
}