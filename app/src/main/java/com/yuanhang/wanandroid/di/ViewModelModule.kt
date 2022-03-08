package com.yuanhang.wanandroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.ui.homepage.CommonArticleViewModel
import com.yuanhang.wanandroid.ui.homepage.HomePageViewModel
import com.yuanhang.wanandroid.ui.knowledgesquare.KnowledgeSquareFragment
import com.yuanhang.wanandroid.ui.knowledgesquare.KnowledgeSquareViewModel
import com.yuanhang.wanandroid.ui.knowledgesystem.KnowLedgeSystemResultViewModel
import com.yuanhang.wanandroid.ui.knowledgesystem.KnowledgeSystemFragment
import com.yuanhang.wanandroid.ui.knowledgesystem.KnowledgeSystemViewModel
import com.yuanhang.wanandroid.ui.login.LoginViewModel
import com.yuanhang.wanandroid.ui.main.MainViewModel
import com.yuanhang.wanandroid.ui.my.UserInfoViewModel
import com.yuanhang.wanandroid.ui.project.ProjectListViewModel
import com.yuanhang.wanandroid.ui.project.ProjectViewModel
import com.yuanhang.wanandroid.ui.search.SearchViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: WanAndroidViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    abstract fun bindHomePageViewModel(homePageViewModel: HomePageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun bindUserInfoViewModel(userInfoViewModel: UserInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommonArticleViewModel::class)
    abstract fun bindCommonArticleViewModel(commonArticleViewModel: CommonArticleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KnowledgeSystemViewModel::class)
    abstract fun bindKnowledgeSystemViewModel(knowledgeSystemViewModel: KnowledgeSystemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KnowledgeSquareViewModel::class)
    abstract fun bindKnowledgeSquareViewModel(knowledgeSquareViewModel: KnowledgeSquareViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KnowLedgeSystemResultViewModel::class)
    abstract fun bindKnowledgeSquareResultViewModel(knowledgeSquareResultViewModel: KnowLedgeSystemResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProjectViewModel::class)
    abstract fun bindProjectViewModel(projectViewModel: ProjectViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProjectListViewModel::class)
    abstract fun bindProjectListViewModel(projectListViewModel: ProjectListViewModel): ViewModel
}