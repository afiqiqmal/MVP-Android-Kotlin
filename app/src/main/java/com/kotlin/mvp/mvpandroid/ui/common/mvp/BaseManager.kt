package com.kotlin.mvp.mvpandroid.ui.common.mvp

import com.java.mvp.factory.internal.RestApi

import javax.inject.Singleton

/**
* @author by hafiq on 25/01/2018.
*/

@Singleton
open class BaseManager {
    var restApi: RestApi? = null
}
