package com.kotlin.mvp.mvpandroid.ui.splash

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.java.mvp.factory.entity.request.TokenRequest
import com.java.mvp.factory.entity.response.TokenResponse
import com.kotlin.mvp.mvpandroid.internal.activity.PerActivity
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import com.kotlin.mvp.mvpandroid.services.RegistrationIntentService
import com.kotlin.mvp.mvpandroid.ui.common.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
class SplashPresenter

@Inject
internal constructor(private val manager: SplashManager, private val preferences: PreferencesRepository) : BasePresenter() {

    private lateinit var mView: SplashConnector
    private val mSubscription = CompositeDisposable()
    private val tokenBroadcastReceiver: TokenBroadCastService

    init {
        tokenBroadcastReceiver = TokenBroadCastService()
    }

    fun getToken(request: TokenRequest) {
        val s = manager.getToken(request)
                .doOnSubscribe { _ -> showLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tokenResponse ->
                    preferences.authToken = tokenResponse.token
                    showContents(tokenResponse)
                }) { throwable ->
                    throwable.printStackTrace()
                    showError(throwable)
                }
        mSubscription.add(s)
    }

    internal fun requestPushToken(activity: Activity?) {
        if (activity != null) {
            val service = Intent(activity, RegistrationIntentService::class.java)
            service.putExtra(RegistrationIntentService.TOKEN, "token")
            activity.startService(service)

            val intentFilter = IntentFilter(RegistrationIntentService.ACTION_TOKEN)
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
            activity.registerReceiver(tokenBroadcastReceiver, intentFilter)

            //send back first
            sendBroadCast()
        }
    }

    inner class TokenBroadCastService : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val token = intent.getStringExtra(RegistrationIntentService.TOKEN)
            sendToken(token)
        }
    }

    internal fun voidReceiver(activity: Activity) {
        try {
            activity.unregisterReceiver(tokenBroadcastReceiver)
        } catch (ignored: Exception) {
        }

    }

    private fun sendBroadCast() {
        mView.sendBroadCast(tokenBroadcastReceiver)
    }

    private fun sendToken(token: String) {
        mView.sendToken(token)
    }

    override fun showLoading() {
        mView.showLoading()
    }

    override fun showContents(response: Any) {
        mView.showContents(response as TokenResponse)
    }

    override fun showError(error: Throwable) {
        mView.showError(error)
    }

    override fun setView(view: Any) {
        mView = view as SplashConnector
    }
}