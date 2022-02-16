package com.yuanhang.wanandroid.api

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.WanAndroidApplication
import com.yuanhang.wanandroid.model.LoginInfo
import com.yuanhang.wanandroid.model.UserInfo
import com.yuanhang.wanandroid.util.SPUtils
import com.yuanhang.wanandroid.util.jsonDumps
import com.yuanhang.wanandroid.util.jsonLoads

/**
 * created by yuanhang on 2022/2/16
 * description:
 */
object CommonInfoStore {

    var loginInfo: LoginInfo? = null
        get() {
            if (field == null) {
                field = jsonLoads(SPUtils.get(WanAndroidApplication.appContext, SPUtils.KEY.LOGIN_INFO, ""), LoginInfo::class.java)
            }
            return field
        }
        set(value) {
            field = if (value != null) {
                jsonDumps(value)?.let { loginInfoJsonString ->
                    SPUtils.put(WanAndroidApplication.appContext, SPUtils.KEY.LOGIN_INFO, loginInfoJsonString)
                }
                value
            } else {
                SPUtils.put(WanAndroidApplication.appContext, SPUtils.KEY.LOGIN_INFO, "")
                null
            }
        }

    var userInfo: MutableLiveData<UserInfo>? = null
        get() {
            if (field == null) {
                val userInfoJsonString = SPUtils.get(WanAndroidApplication.appContext, SPUtils.KEY.USER_INFO, "")
                field = MutableLiveData<UserInfo>().apply {
                    jsonLoads(userInfoJsonString, UserInfo::class.java)?.let {
                        postValue(it)
                    }
                }
            }
            return field
        }

    fun saveUserInfo(info: UserInfo, needNotice: Boolean = true) {
        jsonDumps(info)?.let {
            SPUtils.put(WanAndroidApplication.appContext, SPUtils.KEY.USER_INFO, it)
        }
        if (needNotice) {
            userInfo?.value = info
        }
    }
}