package com.kotlin.mvp.mvpandroid.internal.fragment

import android.app.Activity
import com.kotlin.mvp.mvpandroid.internal.activity.PerActivity
import dagger.Module
import dagger.Provides

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