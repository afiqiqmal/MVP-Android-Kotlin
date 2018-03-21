package com.kotlin.mvp.mvpandroid.ui.common

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.WindowManager
import butterknife.ButterKnife
import butterknife.Unbinder
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.kotlin.mvp.mvpandroid.MVPApplication
import com.kotlin.mvp.mvpandroid.analytics.AnalyticHelper
import com.kotlin.mvp.mvpandroid.internal.AppComponent
import com.kotlin.mvp.mvpandroid.internal.fragment.FragmentComponent
import com.kotlin.mvp.mvpandroid.internal.fragment.FragmentModule
import com.kotlin.mvp.mvpandroid.permission.RxPermissionHelper
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import com.kotlin.mvp.mvpandroid.utils.DeviceUtils
import com.kotlin.mvp.mvpandroid.utils.ErrorUtils
import com.kotlin.mvp.mvpandroid.utils.TypeFaceUtils
import javax.inject.Inject

/**
 * @author : hafiq on 23/01/2017.
 */

class BaseFragment : Fragment() {

    private var mSubscriptions: CompositeDisposable? = null
    private var mIsSubscriber = false

    private var unbinder: Unbinder? = null

    @Inject
    lateinit var preferencesRepository: PreferencesRepository;

    @Inject
    lateinit var errorUtils: ErrorUtils

    @Inject
    lateinit var analyticHelper: AnalyticHelper

    @Inject
    lateinit var typeFaceUtils: TypeFaceUtils

    internal var activity: Activity? = null

    @Inject
    lateinit var rxPermissions: RxPermissionHelper

    val appComponent: AppComponent
        get() = MVPApplication.daggerAppComponent(getActivity()!!.applicationContext)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActivity()!!.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getActivity()!!.window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)

        fragmentComponent().inject(this)
        rxPermissions.setRxPermissions(RxPermissions(getActivity()!!))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)
    }

    override fun onStop() {
        super.onStop()

        unsubscribeAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (unbinder != null)
            unbinder!!.unbind()
    }


    protected fun fragmentComponent(): FragmentComponent {
        return appComponent.fragmentComponent(FragmentModule(getActivity()!!))
    }


    protected fun addSubscription(s: Disposable) {
        if (mSubscriptions == null) mSubscriptions = CompositeDisposable()
        mSubscriptions!!.add(s)
    }

    protected fun unsubscribeAll() {
        if (mSubscriptions == null) return
        mSubscriptions!!.clear()
    }
}
