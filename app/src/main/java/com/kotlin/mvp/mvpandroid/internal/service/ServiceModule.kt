package com.kotlin.mvp.mvpandroid.internal.service


import android.app.Service

import dagger.Module
import dagger.Provides

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
class ServiceModule(private val mContext: Service) {

    @Provides
    @PerService
    internal fun provideService(): Service {
        return mContext
    }
}
