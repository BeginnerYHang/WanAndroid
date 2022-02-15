package com.yuanhang.wanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}