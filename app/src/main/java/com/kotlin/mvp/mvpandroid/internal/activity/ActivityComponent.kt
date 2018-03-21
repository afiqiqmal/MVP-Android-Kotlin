package com.kotlin.mvp.mvpandroid.internal.activity


import dagger.Subcomponent
import com.kotlin.mvp.mvpandroid.ui.MainActivity
import com.kotlin.mvp.mvpandroid.ui.common.BaseActivity
import com.kotlin.mvp.mvpandroid.ui.splash.SplashActivity

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
