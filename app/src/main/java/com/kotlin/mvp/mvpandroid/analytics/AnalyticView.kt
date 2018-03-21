package com.kotlin.mvp.mvpandroid.analytics

import android.os.Bundle

/**
 * @author : hafiq on 23/01/2017.
 */

internal interface AnalyticView {

    fun sendScreenName(name: String)
    fun sendUserProperties(name: String, value: String)
    fun sendUserIdProperties(name: String)
    fun sendSessionTimeOut(time: Long)
    fun sendEvent(name: String, category: String, content: String)
    fun sendEvent(name: String, bundle: Bundle)
}
