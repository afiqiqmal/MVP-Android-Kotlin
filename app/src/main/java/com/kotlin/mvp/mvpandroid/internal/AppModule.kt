package com.kotlin.mvp.mvpandroid.internal

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
class AppModule(context: Application) {

    private val mContext: Context

    init {
        mContext = context
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return mContext
    }

}
