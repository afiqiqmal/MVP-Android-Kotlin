package com.kotlin.mvp.mvpandroid.utils

import android.content.Context
import android.graphics.Typeface
import com.kotlin.mvp.mvpandroid.R
import javax.inject.Inject

/**
 * @author : hafiq on 01/09/2017.
 */

class TypeFaceUtils @Inject
constructor(private val context: Context) {

    val boldFont: Typeface
        get() = Typeface.createFromAsset(context.assets, context.getString(R.string.font_bold))

    val regularFont: Typeface
        get() = Typeface.createFromAsset(context.assets, context.getString(R.string.font_regular))

    val mediumFont: Typeface
        get() = Typeface.createFromAsset(context.assets, context.getString(R.string.font_medium))

    val thinFont: Typeface
        get() = Typeface.createFromAsset(context.assets, context.getString(R.string.font_thin))

    fun loadFont(path: String): Typeface {
        return Typeface.createFromAsset(context.assets, path)
    }
}
