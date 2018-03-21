package com.kotlin.mvp.mvpandroid.internal.activity


import com.kotlin.mvp.mvpandroid.ui.MainActivity
import com.kotlin.mvp.mvpandroid.ui.common.BaseActivity
import com.kotlin.mvp.mvpandroid.ui.splash.SplashActivity
import dagger.Subcomponent

/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    //Activity
    fun inject(activity: MainActivity)

    fun inject(activity: BaseActivity)
    fun inject(activity: SplashActivity)

}
