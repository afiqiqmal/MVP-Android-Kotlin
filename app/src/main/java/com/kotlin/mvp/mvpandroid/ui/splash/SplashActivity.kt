package com.kotlin.mvp.mvpandroid.ui.splash

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.java.mvp.factory.entity.request.TokenRequest
import com.java.mvp.factory.entity.response.TokenResponse
import com.kotlin.mvp.mvpandroid.R
import com.kotlin.mvp.mvpandroid.analytics.Screen
import com.kotlin.mvp.mvpandroid.ui.MainActivity
import com.kotlin.mvp.mvpandroid.ui.common.BaseActivity
import com.kotlin.mvp.mvpandroid.utils.CommonUtils
import com.kotlin.mvp.mvpandroid.utils.DeviceUtils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author : hafiq on 23/01/2017.
 */

class SplashActivity : BaseActivity(), SplashConnector {

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.refresh)
    lateinit var refresh: Button

    private lateinit var tokenBroadcastReceiver: BroadcastReceiver
    internal var version: String? = null

    @Inject
    lateinit var mPresenter: SplashPresenter

    private var isTimerFinished = false
    private var isTokenFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityComponent().inject(this)

        DeviceUtils.addShortcutIcon(this, preferencesRepository)

        mPresenter.setView(this)

        if (CommonUtils.checkPlayServices(this)) {
            version = DeviceUtils.getDeviceVersion(this)

            runProcess()

        } else {
            Toast.makeText(this, R.string.google_service_update, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun runProcess() {
        // run timer for 3 seconds to mask process
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe({ _ ->
                    isTimerFinished = true
                    checkFinished()
                }) { t -> errorUtils.recordError(t) }

        if (preferencesRepository.pushToken == null)
            mPresenter.requestPushToken(this)
        else {
            mPresenter.getToken(requestDetail())
        }
    }

    @OnClick(R.id.refresh)
    fun onRefresh() {
        runOnUiThread { refresh.visibility = View.GONE }
        runProcess()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.voidReceiver(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.voidReceiver(this)
    }

    private fun requestDetail(): TokenRequest {
        return TokenRequest(
                DeviceUtils.getUDID(this),
                getString(R.string.mobile_os),
                DeviceUtils.apIlevel,
                DeviceUtils.getDeviceVersion(this),
                preferencesRepository.pushToken
        )
    }

    override fun showContents(response: TokenResponse) {
        isTokenFinished = true
        checkFinished()
    }

    override fun showLoading() {
        runOnUiThread { progressBar.visibility = View.VISIBLE }
    }

    override fun showError(throwable: Throwable) {
        errorUtils.checkError(throwable)
        try {
            runOnUiThread { progressBar.visibility = View.GONE }
            runOnUiThread { refresh.visibility = View.VISIBLE }
        } catch (e: Exception) {
            errorUtils.recordError(e)
        }

    }

    override fun sendToken(token: String) {
        mPresenter.getToken(requestDetail())
    }

    override fun sendBroadCast(broadcastReceiver: BroadcastReceiver) {
        tokenBroadcastReceiver = broadcastReceiver
    }

    private fun checkFinished() {
        if (isTimerFinished && isTokenFinished) {

            runOnUiThread { progressBar.visibility = View.GONE }

            val intent: Intent = if (preferencesRepository.userPref.hasLogin()!!)
                Intent(Intent(this, MainActivity::class.java))
            else
                Intent(Intent(this, MainActivity::class.java))

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onResume() {
        super.onResume()

        analyticHelper.sendScreenName(Screen.SPLASHSCREEN)
    }
}