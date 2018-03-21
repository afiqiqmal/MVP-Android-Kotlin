package com.kotlin.mvp.mvpandroid.internal.network

import android.content.Context
import com.java.mvp.factory.internal.Constant.CACHE_CONTROL
import com.kotlin.mvp.mvpandroid.utils.DeviceUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author : hafiq on 23/03/2017.
 */

class CacheInterceptor(private val mContext: Context) : Interceptor {
    private var durationCache = 60 * 60 * 24 * 7 // 1 week
    private var cacheAge = 60 // 1 minutes

    fun setDurationCache(durationCache: Int) {
        this.durationCache = durationCache
    }

    fun setCacheAge(cacheAge: Int) {
        this.cacheAge = cacheAge
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        if (request.method() == "GET") {
            request = if (DeviceUtils.isConnected(mContext)) {
                request.newBuilder()
                        .header(CACHE_CONTROL, "public, max-age=$cacheAge")
                        .build()
            } else {
                request.newBuilder()
                        .header(CACHE_CONTROL, "public, only-if-cached, max-stale=$durationCache")
                        .build()
            }
        }

        return chain.proceed(request)
    }
}