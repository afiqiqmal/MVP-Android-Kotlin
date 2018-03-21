package com.kotlin.mvp.mvpandroid.utils

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.support.annotation.RequiresPermission
import com.kotlin.mvp.mvpandroid.R
import com.kotlin.mvp.mvpandroid.listener.DialogPositionClickCallback
import com.mlsdev.rximagepicker.RxImagePicker
import com.mlsdev.rximagepicker.Sources

/**
* @author by hafiq on 13/12/2017.
*/

object DialogUtils {

    @RequiresPermission(allOf = [(Manifest.permission.WRITE_EXTERNAL_STORAGE), (Manifest.permission.READ_EXTERNAL_STORAGE), (Manifest.permission.CAMERA)])
    fun getImageFromGallery(context: Context, dialogPositionClickCallback: DialogPositionClickCallback?) {
        if (dialogPositionClickCallback == null) {
            throw RuntimeException("DialogPositionClickCallback is needed")
        }
        val items = arrayOf<CharSequence>("Gallery", "Camera")
        val alert = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        alert.setTitle("Choose Attachment")
        alert.setItems(items) { dialogInterface, item ->
            if (item == 0) {
                getImageUri(context, Sources.GALLERY, dialogPositionClickCallback)
            } else {
                getImageUri(context, Sources.CAMERA, dialogPositionClickCallback)
            }

            dialogInterface.dismiss()
        }
        alert.setOnDismissListener { _ ->  }
        alert.setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.dismiss() }
        alert.show()
    }


    private fun getImageUri(context: Context, sources: Sources, dialogPositionClickCallback: DialogPositionClickCallback?) {
        RxImagePicker.with(context).requestImage(sources).subscribe({ dialogPositionClickCallback!!.imageDialogCallback(it) })
    }

}
