package com.kotlin.mvp.mvpandroid.permission

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import com.kotlin.mvp.mvpandroid.R
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * @author : hafiq on 28/07/2017.
 */

class RxPermissionHelper(private val context: Context) {
    private var rxPermissions: RxPermissions? = null

    fun setRxPermissions(rxPermissions: RxPermissions) {
        this.rxPermissions = rxPermissions
    }

    fun requestPermissions(connector: RxPermissionView.PermissionConnector, vararg permission: String) {
        check()
        rxPermissions!!
                .request(*permission)
                .subscribe({ connector.isPermissionGranted(it) })
    }

    fun requestEachPermissions(connector: RxPermissionView.MultiplePermissionConnector, vararg permissions: String) {
        check()
        rxPermissions!!
                .requestEach(*permissions)
                .subscribe { permission ->
                    when {
                        permission.granted -> connector.isPermissionGranted(permission.name, true)
                        permission.shouldShowRequestPermissionRationale -> connector.isPermissionShouldShowRationale(permission.name)
                        else -> connector.isPermissionGranted(permission.name, false)
                    }
                }
    }

    fun requestPermissionAgain(connector: RxPermissionView.PermissionConnector, vararg permissions: String) {
        val requestAgain = AlertDialog.Builder(context, R.style.AlertDialogCustom)
        requestAgain.setCancelable(false)
        requestAgain.setTitle(R.string.permission_text)
        requestAgain.setMessage(R.string.permission_explain2_text)
        requestAgain.setPositiveButton(R.string.request_again_text) { _, _ -> requestPermissions(connector, *permissions) }
        requestAgain.setNegativeButton(R.string.no_text) { dialog, _ ->
            dialog.dismiss()
            System.exit(0)
        }
        requestAgain.create().show()
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun showMessageRationale() {
        AlertDialog.Builder(context, R.style.AlertDialogCustom).setTitle(R.string.attention_text)
                .setCancelable(false)
                .setMessage(R.string.permission_explain_text)
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                .show()
    }

    private fun check() {
        if (rxPermissions == null) {
            throw RuntimeException("RxPermissions need to be set first")
        }
    }
}
