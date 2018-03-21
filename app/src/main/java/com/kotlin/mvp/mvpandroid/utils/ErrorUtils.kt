package com.kotlin.mvp.mvpandroid.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.google.gson.Gson
import com.java.mvp.factory.entity.response.ErrorResponse
import com.java.mvp.factory.internal.Constant
import com.kotlin.mvp.mvpandroid.R
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
class ErrorUtils {

    private var mContext: Context? = null
    private val LOG = false
    private var throwable: Throwable? = null

    private lateinit var preferencesRepository: PreferencesRepository

    @Inject
    constructor(context: Context, preferencesRepository: PreferencesRepository) {
        this.mContext = context
        this.preferencesRepository = preferencesRepository
    }

    constructor(context: Context) {
        this.mContext = context
    }

    fun checkError(e: Throwable) {
        try {
            throwable = e
            Crashlytics.logException(e)
            e.printStackTrace()

            if (e is HttpException) {
                val code = getHttpErrorCode(e)

                try {
                    val responseBody = e.response().errorBody()!!
                    val response = Gson().fromJson(responseBody.string(), ErrorResponse::class.java)
                    httpMessage(response.message)

                    if (response.isShould_quit) {
                        preferencesRepository.pref.clearChamber()
                    } else if (response.isShould_login) {
                        preferencesRepository.pref.clearChamber()
                    }

                } catch (en: Exception) {
                    en.printStackTrace()
                    httpMessage(code)
                }

            } else if (e is ConnectException) {
                showToast(mContext!!.getString(R.string.slow_internet))
            } else if (e is UnknownHostException || e is SocketTimeoutException) {
                showToast(mContext!!.getString(R.string.internet_not_connected))
            } else if (e is SSLHandshakeException || e is SSLPeerUnverifiedException) {
                showToast(mContext!!.getString(R.string.server_problem))
            } else {
                showToast(mContext!!.getString(R.string.unknown_error_msg))
            }
        } catch (err: Exception) {
            err.printStackTrace()
        }

    }

    private fun getHttpErrorCode(e: Throwable): Int {
        val body = (e as HttpException).response()
        return body.code()
    }

    private fun httpMessage(custom: String?) {
        showToast(custom)
    }

    //only common http error
    private fun httpMessage(code: Int) {
        if (code == 400) {
            showToast("Bad Request")
        } else if (code == 401) {
            showToast("No Authorize Access")
        } else if (code == 403) {
            showToast("Forbidden Access")
        } else if (code == 404) {
            showToast("Request Not Found")
        } else if (code == 405) {
            showToast("Request Not Allowed")
        } else if (code == 407) {
            showToast("Proxy Authentication Required")
        } else if (code == 408) {
            showToast("Data Request Expired")
        } else if (code == 500) {
            showToast("Internal Server Error Occurred")
        } else if (code == 502) {
            showToast("Bad Url Gateway")
        } else if (code == 503) {
            showToast("Service is Unavailable. Please Try Again")
        } else {
            showErrorLog("Error code : $code")
        }
    }

    private fun showToast(message: String?) {
        if (!LOG) {
            if (mContext != null) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
            }
        }
        showErrorLog(message + ": " + throwable!!.message)
    }

    private fun showErrorLog(message: String) {
        Log.e(Constant.LOGTAG, message)
    }

    fun recordError(e: Throwable) {
        try {
            Crashlytics.logException(e)
            e.printStackTrace()
            Crashlytics.log(e.message)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
}
