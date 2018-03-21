package com.kotlin.mvp.mvpandroid.internal.service


import dagger.Subcomponent
import com.kotlin.mvp.mvpandroid.services.RegistrationIntentService


/**
 * @author : hafiq on 23/01/2017.
 */

@PerService
@Subcomponent(modules = [(ServiceModule::class)])
interface ServiceComponent {
    fun inject(service: RegistrationIntentService)
}