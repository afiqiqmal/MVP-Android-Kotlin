package com.kotlin.mvp.mvpandroid.internal


import dagger.Component
import com.kotlin.mvp.mvpandroid.internal.activity.ActivityComponent
import com.kotlin.mvp.mvpandroid.internal.activity.ActivityModule
import com.kotlin.mvp.mvpandroid.internal.fragment.FragmentComponent
import com.kotlin.mvp.mvpandroid.internal.fragment.FragmentModule
import com.kotlin.mvp.mvpandroid.internal.network.ApiModule
import com.kotlin.mvp.mvpandroid.internal.network.BaseOkHttpClient
import com.kotlin.mvp.mvpandroid.internal.service.ServiceComponent
import com.kotlin.mvp.mvpandroid.internal.service.ServiceModule
import com.kotlin.mvp.mvpandroid.permission.RxPermissionModule
import com.kotlin.mvp.mvpandroid.prefs.PreferenceModule
import javax.inject.Singleton

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
@Component(modules = [(AppModule::class), (PreferenceModule::class), (RxPermissionModule::class), (BaseOkHttpClient::class), (ApiModule::class)])
interface AppComponent {
    fun activityComponent(module: ActivityModule): ActivityComponent
    fun fragmentComponent(module: FragmentModule): FragmentComponent
    fun serviceComponent(module: ServiceModule): ServiceComponent
}


