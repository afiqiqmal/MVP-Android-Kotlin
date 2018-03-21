package com.kotlin.mvp.mvpandroid.ui.common

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import butterknife.ButterKnife
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.kotlin.mvp.mvpandroid.MVPApplication
import com.kotlin.mvp.mvpandroid.analytics.AnalyticHelper
import com.kotlin.mvp.mvpandroid.internal.AppComponent
import com.kotlin.mvp.mvpandroid.internal.activity.ActivityComponent
import com.kotlin.mvp.mvpandroid.internal.activity.ActivityModule
import com.kotlin.mvp.mvpandroid.permission.RxPermissionHelper
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import com.kotlin.mvp.mvpandroid.utils.DeviceUtils
import com.kotlin.mvp.mvpandroid.utils.ErrorUtils
import com.kotlin.mvp.mvpandroid.utils.TypeFaceUtils
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

/**
 * @author : hafiq on 28/07/2017.
 */

open class BaseActivity : AppCompatActivity() {

    protected var mSubscriptions: CompositeDisposable? = null

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @Inject
    lateinit var errorUtils: ErrorUtils

    @Inject
    lateinit var analyticHelper: AnalyticHelper


    @Inject
    lateinit var typeFaceUtils: TypeFaceUtils

    @Inject
    lateinit var rxPermissions: RxPermissionHelper

    val appComponent: AppComponent
        get() = MVPApplication.daggerAppComponent(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent().inject(this)

        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)

        DeviceUtils.closeSoftKeyboard(this)
        rxPermissions.setRxPermissions(RxPermissions(this))
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onStop() {
        super.onStop()
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    protected fun activityComponent(): ActivityComponent {
        return appComponent.activityComponent(ActivityModule(this))
    }

    protected fun addSubscription(s: Disposable) {
        if (mSubscriptions == null) mSubscriptions = CompositeDisposable()
        mSubscriptions!!.add(s)
    }

    protected fun unsubscribeAll() {
        if (mSubscriptions == null) return
        mSubscriptions!!.clear()
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
