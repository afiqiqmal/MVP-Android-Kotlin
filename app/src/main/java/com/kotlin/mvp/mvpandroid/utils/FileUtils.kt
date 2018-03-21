package com.kotlin.mvp.mvpandroid.utils

import android.content.Context
import java.io.IOException

/**
 * Created by hafiq on 15/12/2017.
 */

object FileUtils {
    private val UTF8 = "UTF-8"

    fun loadJSONFromAsset(context: Context, filename: String): String? {
        val json: String?
        try {
            val `is` = context.assets.open(filename)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }
}
