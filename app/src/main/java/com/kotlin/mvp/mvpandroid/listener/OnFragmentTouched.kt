package com.kotlin.mvp.mvpandroid.listener

import android.support.v4.app.Fragment

/**
 * @author : hafiq on 29/10/2017.
 */

interface OnFragmentTouched {
    fun onFragmentTouched(fragment: Fragment, x: Float, y: Float)
}
