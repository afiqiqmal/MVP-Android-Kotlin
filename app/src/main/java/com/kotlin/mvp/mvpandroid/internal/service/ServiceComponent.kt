package com.kotlin.mvp.mvpandroid.internal.service


import com.kotlin.mvp.mvpandroid.services.RegistrationIntentService
import dagger.Subcomponent


/**
 * @author : hafiq on 23/01/2017.
 */

@PerService
@Subcomponent(modules = [(ServiceModule::class)])
interface ServiceComponent {
    fun inject(service: RegistrationIntentService)
}