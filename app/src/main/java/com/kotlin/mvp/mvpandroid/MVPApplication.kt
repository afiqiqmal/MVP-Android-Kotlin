package com.kotlin.mvp.mvpandroid

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.StrictMode
import com.chamber.kotlin.library.SharedChamber
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.firebase.analytics.FirebaseAnalytics
import com.kotlin.mvp.mvpandroid.internal.AppComponent
import com.kotlin.mvp.mvpandroid.internal.AppModule
import com.kotlin.mvp.mvpandroid.internal.DaggerAppComponent
import io.fabric.sdk.android.Fabric
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * @author : hafiq on 23/01/2017.
 */

class MVPApplication : Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        try {
            SharedChamber.initChamber(this)

            CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                    .setDefaultFontPath(getString(R.string.font_regular))
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            )

            appComponent = DaggerAppComponent
                    .builder()
                    .appModule(AppModule(this))
                    .build()

            errorHandler()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun errorHandler() {
        try {
            val crashlytics = Crashlytics.Builder()
                    .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                    .build()
            Fabric.with(this, crashlytics)

            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(BuildConfig.DEBUG)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun setDebugConfigs() {
        StrictMode.enableDefaults()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    companion object {

        fun daggerAppComponent(context: Context): AppComponent? {
            val app = context.applicationContext as MVPApplication
            return app.appComponent
        }


        fun getApp(c: Context): MVPApplication {
            return c.applicationContext as MVPApplication
        }
    }
}
