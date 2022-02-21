package com.yuanhang.wanandroid.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.onClick
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    fun initView() {
        etSearch.addTextChangedListener {
            if (it.isNullOrBlank()) {
                ivClear.gone()
            } else {
                ivClear.visible()
            }
        }
        tvCancel.onClick {
            onBackPressed()
        }
        ivClear.onClick {
            hottestWordsGroup.visible()
            etSearch.setText("")
        }
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                val keyWord = etSearch.text.toString().trim()
                if (keyWord.isEmpty()) {
                    toast(getString(R.string.search_input_not_empty))
                    return@setOnEditorActionListener false
                }
                search(keyWord)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun search(keyWord: String) {
        hottestWordsGroup.gone()
    }

    companion object{
        fun start(from: BaseActivity) {
            val intent = Intent(from, SearchActivity:: class.java)
            from.startActivity(intent)
        }
    }
}