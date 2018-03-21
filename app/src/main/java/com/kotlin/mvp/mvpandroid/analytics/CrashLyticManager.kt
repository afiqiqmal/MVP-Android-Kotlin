package com.kotlin.mvp.mvpandroid.analytics

import android.content.Context
import android.os.Bundle

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.crashlytics.android.answers.LoginEvent
import com.crashlytics.android.answers.SignUpEvent

/**
 * @author : hafiq on 28/04/2017.
 */

internal class CrashLyticManager(private val mContext: Context) : AnalyticView {

    override fun sendScreenName(name: String) {
        Answers.getInstance().logCustom(CustomEvent("Screen").putCustomAttribute("Page", name))
    }

    override fun sendUserProperties(name: String, value: String) {
        when (name) {
            AnalyticConstant.USER_EMAIL -> Crashlytics.setUserEmail(value)
            AnalyticConstant.USER_NAME -> Crashlytics.setUserName(value)
            AnalyticConstant.USER_SIGN_IN -> Answers.getInstance().logLogin(LoginEvent().putMethod(value).putSuccess(true))
            AnalyticConstant.USER_SIGN_UP -> Answers.getInstance().logSignUp(SignUpEvent().putMethod(value).putSuccess(true))
            else -> Answers.getInstance().logCustom(CustomEvent("Custom_Event").putCustomAttribute(name, value))
        }

    }

    override fun sendUserIdProperties(name: String) {
        Crashlytics.setUserIdentifier(name)
    }

    override fun sendSessionTimeOut(time: Long) {

    }

    override fun sendEvent(name: String, category: String, content: String) {
        Answers.getInstance().logCustom(CustomEvent(category).putCustomAttribute(name, content))
    }

    override fun sendEvent(name: String, bundle: Bundle) {

    }
}
