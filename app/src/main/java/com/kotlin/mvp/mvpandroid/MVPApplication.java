package com.kotlin.mvp.mvpandroid;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.StrictMode;

import com.chamber.kotlin.library.SharedChamber;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;

import com.kotlin.mvp.mvpandroid.internal.AppComponent;
import com.kotlin.mvp.mvpandroid.internal.AppModule;
import com.kotlin.mvp.mvpandroid.internal.DaggerAppComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * @author : hafiq on 23/01/2017.
 */

public class MVPApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            SharedChamber.initChamber(this);

            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(getString(R.string.font_regular))
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );

            setAppComponent(DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(this))
                    .build());

            errorHandler();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void errorHandler(){
        try {
            Crashlytics crashlytics = new Crashlytics.Builder()
                    .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                    .build();
            Fabric.with(this, crashlytics);

            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(BuildConfig.DEBUG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public static AppComponent daggerAppComponent(Context context) {
        MVPApplication app = (MVPApplication) context.getApplicationContext();
        return app.getAppComponent();
    }

    private void setDebugConfigs() {
        StrictMode.enableDefaults();
    }


    public static MVPApplication getApp(Context c) {
        return (MVPApplication) c.getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
