package com.kotlin.mvp.mvpandroid.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.kotlin.mvp.mvpandroid.MVPApplication
import com.kotlin.mvp.mvpandroid.internal.service.ServiceModule
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import javax.inject.Inject

/**
 * @author : hafiq on 07/02/2017.
 */

class RegistrationIntentService : IntentService(TAG) {

    @Inject
    lateinit var user: PreferencesRepository

    override fun onCreate() {
        super.onCreate()
        MVPApplication.daggerAppComponent(this)!!.serviceComponent(ServiceModule(this)).inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {

        var iid = FirebaseInstanceId.getInstance()
        var token = iid.token

        while (token == null) {
            iid = FirebaseInstanceId.getInstance()
            token = iid.token
        }


        val response = Intent()
        response.putExtra(TOKEN, token)
        response.action = ACTION_TOKEN
        response.addCategory(Intent.CATEGORY_DEFAULT)
        sendBroadcast(response)


        handleToken(token)
    }

    private fun handleToken(token: String) {
        user.isPushTokenSent = true
        user.savePushToken(token)

        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.i(TAG, token)
    }

    companion object {

        private const val TAG = "RegIntentService"
        const val TOKEN = "token"
        const val ACTION_TOKEN = "TOKEN_RESPONSE"
    }

}
