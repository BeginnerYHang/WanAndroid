package com.yuanhang.wanandroid.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.google.android.flexbox.FlexboxLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.ui.homepage.CommonArticleFragment
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.onClick
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private lateinit var mViewModel: SearchViewModel
    private lateinit var mHottestWordAdapter: HottestWordAdapter
    private var mResultFragment: CommonArticleFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        mViewModel = getViewModel(this, SearchViewModel::class.java)
        initView()
    }

    fun initView() {
        etSearch.addTextChangedListener {
            if (it.isNullOrBlank()) {
                ivClear.gone()
                articleFragmentContainer.gone()
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
        mHottestWordAdapter = HottestWordAdapter()
        rvSearchHottestWords.apply {
            layoutManager = FlexboxLayoutManager(this@SearchActivity)
            adapter = mHottestWordAdapter
            addItemDecoration(HottestWordDecoration())
        }
        getHottestWord()
    }

    fun search(keyWord: String) {
        hottestWordsGroup.gone()
        articleFragmentContainer.visible()
        if (mResultFragment == null) {
            mResultFragment = CommonArticleFragment.newInstance(keyWord)
            supportFragmentManager.beginTransaction()
                .add(R.id.articleFragmentContainer, mResultFragment!!)
                .commit()
        } else {
            mResultFragment!!.setKeyWord(keyWord)
        }
    }

    fun getHottestWord() {
        mViewModel.getHottestWord().observe(this) {
            when(it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    it.data?.let {
                        mHottestWordAdapter.setData(it)
                    }
                }
                Status.ERROR -> {
                    toastInform(it.message?: "")
                }
            }
        }
    }

    companion object{
        fun start(from: BaseActivity) {
            val intent = Intent(from, SearchActivity:: class.java)
            from.startActivity(intent)
        }
    }
}