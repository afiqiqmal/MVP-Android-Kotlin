package com.kotlin.mvp.mvpandroid.internal.fragment

import android.app.Activity

import dagger.Module
import dagger.Provides
import com.kotlin.mvp.mvpandroid.internal.activity.PerActivity

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
class FragmentModule(private val mContext: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return mContext
    }
}