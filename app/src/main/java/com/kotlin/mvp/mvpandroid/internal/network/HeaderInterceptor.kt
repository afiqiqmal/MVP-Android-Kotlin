package com.kotlin.mvp.mvpandroid.internal.network

import android.content.Context
import com.java.mvp.factory.internal.Constant.APP_HEADER
import com.java.mvp.factory.internal.Constant.AUTH
import com.java.mvp.factory.internal.Constant.CLIENT_SERVICE
import com.java.mvp.factory.internal.Constant.HEADER_ACCEPT
import com.java.mvp.factory.internal.Constant.HEADER_AUTHORIZE
import com.java.mvp.factory.internal.Constant.POWERED_BY
import com.kotlin.mvp.mvpandroid.BuildConfig
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import com.kotlin.mvp.mvpandroid.utils.BaseCryptUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author : hafiq on 23/03/2017.
 */

class HeaderInterceptor(private val mContext: Context, private val mPrefs: PreferencesRepository) : Interceptor {
    private var mAuth: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        mAuth = if (mPrefs.authToken != null) mPrefs.authToken else ""

        val builder = chain.request().newBuilder()
        builder.addHeader(HEADER_ACCEPT, APP_HEADER)
                .addHeader(HEADER_AUTHORIZE, AUTH + mAuth!!)
                .addHeader(CLIENT_SERVICE, BaseCryptUtils().decodeStringWithIteration(BuildConfig.CLIENT_SERVICE))
                .addHeader(POWERED_BY, BaseCryptUtils().decodeStringWithIteration(BuildConfig.X_POWERED_BY))

        val r = builder.build()

        return chain.proceed(r)
    }
}