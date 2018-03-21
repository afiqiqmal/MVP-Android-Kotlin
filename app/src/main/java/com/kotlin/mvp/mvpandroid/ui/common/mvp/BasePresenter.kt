package com.kotlin.mvp.mvpandroid.ui.common.mvp

/**
 * Created by hafiq on 25/01/2018.
 */

abstract class BasePresenter {

    abstract fun showLoading()
    abstract fun showContents(response: Any)
    abstract fun showError(error: Throwable)
    abstract fun setView(view: Any)
}
