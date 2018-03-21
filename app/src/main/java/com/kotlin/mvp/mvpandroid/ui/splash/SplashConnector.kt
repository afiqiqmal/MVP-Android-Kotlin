package com.kotlin.mvp.mvpandroid.ui.splash

import android.content.BroadcastReceiver

import com.java.mvp.factory.entity.response.TokenResponse


/**
 * @author : hafiq on 23/01/2017.
 */

interface SplashConnector {

    fun showContents(response: TokenResponse)
    fun showLoading()
    fun showError(throwable: Throwable)
    fun sendToken(token: String)
    fun sendBroadCast(broadcastReceiver: BroadcastReceiver)

}
