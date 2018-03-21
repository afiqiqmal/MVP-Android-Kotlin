package com.kotlin.mvp.mvpandroid.analytics

import android.content.Context

import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
class AnalyticHelper @Inject
internal constructor(private val context: Context) {
    private val firebaseAnalytics: FirebaseManager = FirebaseManager(context)
    private val crashLyticManager: CrashLyticManager = CrashLyticManager(context)

    fun sendScreenName(name: String) {
        firebaseAnalytics.sendScreenName(name)
        crashLyticManager.sendScreenName(name)
    }
}
