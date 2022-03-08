package com.yuanhang.wanandroid.di

import com.yuanhang.wanandroid.base.BaseFragment
import com.yuanhang.wanandroid.ui.homepage.CommonArticleFragment
import com.yuanhang.wanandroid.ui.homepage.HomePageFragment
import com.yuanhang.wanandroid.ui.knowledgesquare.KnowledgeSquareFragment
import com.yuanhang.wanandroid.ui.knowledgesystem.ChildSystemFragment
import com.yuanhang.wanandroid.ui.knowledgesystem.KnowledgeSystemFragment
import com.yuanhang.wanandroid.ui.knowledgesystem.NavigationFragment
import com.yuanhang.wanandroid.ui.knowledgesystem.UsefulWebsiteFragment
import com.yuanhang.wanandroid.ui.my.UserInfoFragment
import com.yuanhang.wanandroid.ui.project.ProjectFragment
import com.yuanhang.wanandroid.ui.project.ProjectListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun baseFragmentInjector(): BaseFragment

    @ContributesAndroidInjector
    internal abstract fun homePageFragmentInjector(): HomePageFragment

    @ContributesAndroidInjector
    internal abstract fun knowledgeSquareFragmentInjector(): KnowledgeSquareFragment

    @ContributesAndroidInjector
    internal abstract fun knowledgeSystemFragmentInjector(): KnowledgeSystemFragment

    @ContributesAndroidInjector
    internal abstract fun knowledgeProjectFragmentInjector(): ProjectFragment

    @ContributesAndroidInjector
    internal abstract fun userInfoFragmentInjector(): UserInfoFragment

    @ContributesAndroidInjector
    internal abstract fun commonArticleFragmentInjector(): CommonArticleFragment

    @ContributesAndroidInjector
    internal abstract fun childSystemFragmentInjector(): ChildSystemFragment

    @ContributesAndroidInjector
    internal abstract fun usefulWebsiteFragmentInjector(): UsefulWebsiteFragment

    @ContributesAndroidInjector
    internal abstract fun navigationFragmentInjector(): NavigationFragment

    @ContributesAndroidInjector
    internal abstract fun projectListFragmentInjector(): ProjectListFragment
}