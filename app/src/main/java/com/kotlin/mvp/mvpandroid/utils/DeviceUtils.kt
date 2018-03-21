package com.kotlin.mvp.mvpandroid.utils

import android.Manifest
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import com.java.mvp.factory.internal.Constant
import com.kotlin.mvp.mvpandroid.R
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import com.kotlin.mvp.mvpandroid.ui.splash.SplashActivity
import java.util.*


/**
 * @author : hafiq on 23/01/2017.
 */

object DeviceUtils {

    val apIlevel: String
        get() = "Android API :" + Build.VERSION.SDK_INT

    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                CommonUtils.capitalize(model)
            } else {
                CommonUtils.capitalize(manufacturer) + " " + model
            }
        }

    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }

    fun getDeviceVersion(activity: Activity): String? {
        val v: String
        return try {
            v = activity.application.packageManager.getPackageInfo(activity.packageName, 0).versionName
            v.replace("-staging", "")
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

    }

    @SuppressLint("HardwareIds")
    fun getUDID(activity: Activity): String {
        return if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val tm = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val tmDevice = "" + tm.deviceId
            val tmSerial = "" + tm.simSerialNumber
            val androidId = "" + Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
            val deviceUuid = UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())

            deviceUuid.toString()
        } else {
            Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
        }
    }

    fun isGPSEnabled(activity: Activity): Boolean {
        val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            Log.e(Constant.LOGTAG, "gps provider error : " + e.message)
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            Log.e(Constant.LOGTAG, "network provider error : " + e.message)
        }

        return gps_enabled && network_enabled
    }

    fun getDeviceEmailAddress(activity: Activity): String {
        val emailPattern = Patterns.EMAIL_ADDRESS
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return ""
        }
        val accounts = AccountManager.get(activity).accounts
        for (account in accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name
            }
        }
        return ""
    }

    fun closeSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun callPhone(context: Context, phoneText: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneText")
        context.startActivity(intent)
    }

    fun intentEmail(context: Context, email: String, title: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        intent.putExtra(Intent.EXTRA_SUBJECT, title)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun addShortcutIcon(context: Context, preferencesRepository: PreferencesRepository) {

        if (!preferencesRepository.isShortCutCreated) {
            val shortcutIntent = Intent(context, SplashActivity::class.java)
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val addIntent = Intent()
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, R.string.app_name)
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher_round))
            addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
            addIntent.putExtra("duplicate", false)
            context.sendBroadcast(addIntent)

            preferencesRepository.setShortCutCreate(true)
        }
    }

}
