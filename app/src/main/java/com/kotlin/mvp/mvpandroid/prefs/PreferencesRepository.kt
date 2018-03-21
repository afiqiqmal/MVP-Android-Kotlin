package com.kotlin.mvp.mvpandroid.prefs


import android.content.Context
import com.chamber.kotlin.library.SharedChamber
import com.chamber.kotlin.library.model.ChamberType
import com.kotlin.mvp.mvpandroid.BuildConfig
import com.kotlin.mvp.mvpandroid.R
import com.kotlin.mvp.mvpandroid.utils.BaseCryptUtils
import javax.inject.Inject


/**
 * @author : hafiq on 07/02/2017.
 */

class PreferencesRepository

@Inject
constructor(private val mContext: Context) {

    val pref: SharedChamber = SharedChamber.ChamberBuilder(mContext)
            .setChamberType(ChamberType.KEY_256)
            .enableCrypto(true, true)
            .setPassword(mContext.getString(R.string.app_name))
            .setFolderName(mContext.getString(R.string.app_name))
            .buildChamber()

    val userPref: SharedChamber.UserChamber
        get() = SharedChamber.UserChamber()

    val isShortCutCreated: Boolean
        get() = pref.getBoolean(SHORTCUT_DEVICE, false)!!

    val isEnableTokenPush: Boolean
        get() = pref.getBoolean(TOKEN_SWITCH, true)!!

    val pushToken: String?
        get() = pref.getString(KEY_PUSH_TOKEN)


    var isPushTokenSent: Boolean
        get() = pref.getBoolean(KEY_PUSH_TOKEN_SENT, false)!!
        set(sent) = pref.put(KEY_PUSH_TOKEN_SENT, sent)

    var serverToken: String?
        get() = pref.getString(KEY_SERVER_TOKEN)
        set(token) = if (token == null) {
            pref.remove(KEY_SERVER_TOKEN)
        } else {
            pref.put(KEY_SERVER_TOKEN, token)
        }

    var authToken: String?
        get() = pref.getString(KEY_AUTH_TOKEN)
        set(auth) = pref.put(KEY_AUTH_TOKEN, auth.toString())

    val url: String
        get() = BaseCryptUtils().decodeStringWithIteration(BuildConfig.URL_API)

    fun setShortCutCreate(bool: Boolean) {
        pref.put(SHORTCUT_DEVICE, bool)
    }

    fun savePushToken(token: String?) {
        if (token == null) {
            pref.remove(KEY_PUSH_TOKEN)
        } else {
            pref.put(KEY_PUSH_TOKEN, token)
        }
    }

    fun enableTokenPush(sent: Boolean) {
        pref.put(TOKEN_SWITCH, sent)
    }


    fun logout() {
        userPref.applyLogin(false)
    }

    companion object {

        private const val KEY_PUSH_TOKEN = "push_token"
        private const val KEY_PUSH_TOKEN_SENT = "push_token_sent"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_SERVER_TOKEN = "server_token"
        private const val KEY_USER = "user"

        private const val SCAN_VERIFIED = "has_verified"
        private const val BEPUNCT_API_URL = "bepunct_api_url"
        private const val BEPUNCT_ACTIVATION_CODE = "bepunct_code"


        private const val USER_SIGN_IN = "user.check.signIn"
        private const val USER_SIGN_IN_TIME = "user.check.signIn.time"

        private const val HOLIDAY = "holiday"
        private const val COMPANY_DETAIL = "company.detail"
        private const val CONFIG_DETAIL = "config"

        private const val SHORTCUT_DEVICE = "shortcut.device.app"

        private const val TOKEN_SWITCH = "token.switch"
    }
}
