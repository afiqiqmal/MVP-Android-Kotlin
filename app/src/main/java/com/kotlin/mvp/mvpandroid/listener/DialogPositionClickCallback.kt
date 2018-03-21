package com.kotlin.mvp.mvpandroid.listener

import android.net.Uri

import io.reactivex.annotations.Nullable

/**
 * Created by hafiq on 10/12/2017.
 */

interface DialogPositionClickCallback {

    fun imageDialogCallback(@Nullable item: Uri)
}
