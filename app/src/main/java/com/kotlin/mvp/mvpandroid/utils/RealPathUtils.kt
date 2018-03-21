package com.kotlin.mvp.mvpandroid.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.CursorLoader

/**
* @author by hafiq on 13/12/2017.
*/

object RealPathUtils {

    @SuppressLint("NewApi")
    fun getRealPathFromURI_API19(context: Context, uri: Uri): String? {
        var filePath: String? = null
        try {

            val wholeID = DocumentsContract.getDocumentId(uri)

            // Split at colon, use second item in the array
            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            val column = arrayOf(MediaStore.Images.Media.DATA)

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, arrayOf(id), null)

            val columnIndex = cursor?.getColumnIndex(column[0]) ?: 0

            if (cursor != null && cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor?.close()
        } catch (e: Exception) {
            return getRealPathFromURI_API11to18(context, uri)
        }

        return filePath
    }


    @SuppressLint("NewApi")
    fun getRealPathFromURI_API11to18(context: Context, contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var result: String? = null
        try {
            val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
            val cursor = cursorLoader.loadInBackground()

            if (cursor != null) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(column_index)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    @SuppressLint("Recycle")
    fun getRealPathFromURI_BelowAPI11(context: Context, contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) ?: 0
        cursor?.moveToFirst()
        return cursor?.getString(column_index)
    }
}