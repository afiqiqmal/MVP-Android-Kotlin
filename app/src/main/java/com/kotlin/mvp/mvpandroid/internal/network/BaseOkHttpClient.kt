package com.kotlin.mvp.mvpandroid.internal.network

import android.content.Context
import com.java.mvp.factory.internal.Constant.CONNECTTIMEOUT
import com.java.mvp.factory.internal.Constant.READTIMEOUT
import com.java.mvp.factory.internal.Constant.WRITETIMEOUT
import dagger.Module
import dagger.Provides
import com.kotlin.mvp.mvpandroid.BuildConfig
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author : hafiq on 19/08/2017.
 */

@Module
class BaseOkHttpClient {

    @Provides
    @Singleton
    fun provideCacheInterceptor(mContext: Context): CacheInterceptor {
        return CacheInterceptor(mContext)
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(mContext: Context, u: PreferencesRepository): HeaderInterceptor {
        return HeaderInterceptor(mContext, u)
    }

    @Provides
    @Singleton
    fun provideClient(mContext: Context, cacheInterceptor: CacheInterceptor, headerInterceptor: HeaderInterceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val maxSize = (10 * 1024 * 1024).toLong() // 10 MiB
        val cache = Cache(File(mContext.cacheDir, "http"), maxSize)

        return if (BuildConfig.DEBUG) {
            okhttp3.OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addInterceptor(headerInterceptor)
                    .connectTimeout(CONNECTTIMEOUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(READTIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(WRITETIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()
        } else {
            okhttp3.OkHttpClient.Builder()
                    .addInterceptor(headerInterceptor)
                    .cache(cache)
                    .addNetworkInterceptor(cacheInterceptor)
                    .connectTimeout(CONNECTTIMEOUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(READTIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(WRITETIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()
        }
    }
}
