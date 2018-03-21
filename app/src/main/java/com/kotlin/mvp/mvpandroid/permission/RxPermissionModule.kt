package com.kotlin.mvp.mvpandroid.permission

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Basyrun Halim
 */

@Module
class RxPermissionModule {
    @Provides
    @Singleton
    fun provideRxPermissionHelper(context: Context): RxPermissionHelper {
        return RxPermissionHelper(context)
    }
}
