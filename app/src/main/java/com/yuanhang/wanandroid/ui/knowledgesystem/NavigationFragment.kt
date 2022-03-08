package com.yuanhang.wanandroid.ui.knowledgesystem

import android.os.Bundle
import android.os.StatFs
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.api.Status
import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseFragment() {

    private lateinit var mViewModel: KnowledgeSystemViewModel
    private lateinit var mNavigationAdapter: NavigationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = getViewModel(this, KnowledgeSystemViewModel::class.java)
        mNavigationAdapter = NavigationAdapter(requireActivity() as MainActivity)
        rvNavigation.apply {
            adapter = mNavigationAdapter
            layoutManager = LinearLayoutManager(context)
        }
        getNavigationItem()
    }

    fun getNavigationItem() {
        mViewModel.getNavigationItem().observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        mNavigationAdapter.setData(it)
                    }
                }
                Status.ERROR -> {
                    toastInform(it.message?: "")
                }
            }
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NavigationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NavigationFragment().apply {

            }
    }
}