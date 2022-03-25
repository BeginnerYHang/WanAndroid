package com.yuanhang.wanandroid.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.WanAndroidApplication
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.ui.common.CommonTipsDialog
import com.yuanhang.wanandroid.ui.login.LoginActivity
import com.yuanhang.wanandroid.util.SPUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import retrofit2.Converter
import java.io.IOException
import java.lang.Exception

internal class CustomMoshiResponseBodyConverter<T>(private val adapter: JsonAdapter<T>) :
    Converter<ResponseBody, T?> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val source = value.source()
        return try {
            // Moshi has no document-level API so the responsibility of BOM skipping falls to whatever
            // is delegating to it. Since it's a UTF-8-only library as well we only honor the UTF-8 BOM.
            if (source.rangeEquals(0, UTF8_BOM)) {
                source.skip(UTF8_BOM.size.toLong())
            }
            val reader = JsonReader.of(source)
            val result = adapter.fromJson(reader)
            if (reader.peek() != JsonReader.Token.END_DOCUMENT) {
                throw JsonDataException("JSON document was not fully consumed.")
            }
            if (result is JsonResponse<*>) {
                if (result.errorCode == -1001) {
                    GlobalScope.launch(Dispatchers.Main) {
                        WanAndroidApplication.app.getCurrentTopActivity()?.let {
                            (it as? BaseActivity)?.let {
                                CommonTipsDialog(it,
                                    it.getString(R.string.info_tip),
                                    it.getString(R.string.login_expired_info),
                                    it.getString(R.string.dialog_confirm),
                                    {
                                        SPUtils.remove(it, SPUtils.KEY.LOGIN_INFO)
                                        CommonInfoStore.loginInfo = null
                                        LoginActivity.start(it, it.localClassName)
                                    }).show()
                            }
                        }
                    }
                    throw Exception("")
                }
            }
            result
        } finally {
            value.close()
        }
    }

    companion object {
        private val UTF8_BOM: ByteString = "EFBBBF".decodeHex()
    }
}