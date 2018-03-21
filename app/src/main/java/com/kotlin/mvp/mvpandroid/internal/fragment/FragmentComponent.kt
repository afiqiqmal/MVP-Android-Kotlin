package com.kotlin.mvp.mvpandroid.internal.fragment

import dagger.Subcomponent
import com.kotlin.mvp.mvpandroid.internal.activity.PerActivity
import com.kotlin.mvp.mvpandroid.ui.common.BaseFragment

/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {

    //Fragment
    fun inject(fragment: BaseFragment)

}
