package com.kotlin.mvp.mvpandroid.internal.activity

import android.app.Activity

import dagger.Module
import dagger.Provides

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
class ActivityModule(private val mContext: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return mContext
    }
}