package com.kotlin.mvp.mvpandroid.analytics

import android.content.Context
import android.os.Bundle

import com.google.firebase.analytics.FirebaseAnalytics

import com.kotlin.mvp.mvpandroid.utils.CommonUtils


/**
 * @author : hafiq on 23/01/2017.
 */

internal class FirebaseManager(private val context: Context) : AnalyticView {
    override fun sendEvent(name: String, category: String, content: String) {
        val eventAction = Bundle()
        eventAction.putString(FirebaseAnalytics.Param.CONTENT_TYPE, category)
        eventAction.putString(FirebaseAnalytics.Param.ITEM_NAME, content)
        mFirebaseAnalytics.logEvent(CommonUtils.analyticFormat(name), eventAction)
    }

    private val mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun sendScreenName(name: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        mFirebaseAnalytics.logEvent(name.toLowerCase().replace("[^A-Za-z0-9]+".toRegex(), "_"), bundle)
    }

    override fun sendEvent(name: String, bundle: Bundle) {
        mFirebaseAnalytics.logEvent(name, bundle)
    }

    override fun sendUserProperties(name: String, value: String) {
        mFirebaseAnalytics.setUserProperty(CommonUtils.analyticFormat(name), value)
    }

    override fun sendUserIdProperties(name: String) {
        mFirebaseAnalytics.setUserId(name)
    }

    override fun sendSessionTimeOut(time: Long) {
        mFirebaseAnalytics.setSessionTimeoutDuration(time)
    }
}
