package com.kotlin.mvp.mvpandroid.permission

/**
 * @author : hafiq on 30/05/2017.
 */

class RxPermissionView {

    interface PermissionConnector {
        fun isPermissionGranted(granted: Boolean)
    }

    interface MultiplePermissionConnector {
        fun isPermissionGranted(name: String, granted: Boolean)
        fun isPermissionShouldShowRationale(name: String)
        fun permissionPermanentDenied(name: String)
        fun isAllPermissionGranted(allGranted: Boolean, permanentlyDenied: Boolean)
    }
}
