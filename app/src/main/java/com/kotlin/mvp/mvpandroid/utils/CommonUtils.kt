package com.kotlin.mvp.mvpandroid.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.DisplayMetrics
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.kotlin.mvp.mvpandroid.R
import io.reactivex.annotations.NonNull
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * @author : hafiq on 23/01/2017.
 */

object CommonUtils {

    val currentTime: String
        @SuppressLint("SimpleDateFormat")
        get() {
            val df = SimpleDateFormat("HH:mm:ss")
            return df.format(Calendar.getInstance().time)
        }

    //convert dp to Px
    fun dpToPx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun scaleBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)

        val scaleX = newWidth / bitmap.width.toFloat()
        val scaleY = newHeight / bitmap.height.toFloat()
        val pivotX = 0f
        val pivotY = 0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY)

        val canvas = Canvas(scaledBitmap)
        canvas.matrix = scaleMatrix
        canvas.drawBitmap(bitmap, 0f, 0f, Paint(Paint.FILTER_BITMAP_FLAG))

        return scaledBitmap
    }

    //get screen size
    fun sizeScreen(): Int {
        return (Resources.getSystem().displayMetrics.widthPixels / Resources.getSystem().displayMetrics.density).toInt()
    }

    //get screen height
    fun getHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    //get witdh screen
    fun getWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(timestamp: Long): String {
        val tt = timestamp * 1000L
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(tt)
        return sdf.format(netDate)
    }

    //get current date
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(format: Boolean): String {
        val time = SimpleDateFormat("dd/MMM/yyyy")
        val calendar = Calendar.getInstance()
        return if (format)
            time.format(calendar.time)
        else
            (calendar.timeInMillis / 1000).toString()
    }


    fun isHtml(s: String?): Boolean {
        val tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>"
        val tagEnd = "\\</\\w+\\>"
        val tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>"
        val htmlEntity = "&[a-zA-Z][a-zA-Z0-9]+;"
        val htmlPattern = Pattern.compile("($tagStart.*$tagEnd)|($tagSelfClosing)|($htmlEntity)", Pattern.DOTALL)

        var ret = false
        if (s != null) {
            ret = htmlPattern.matcher(s).find()
        }
        return ret
    }


    fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }

    fun isEmailValid(email: String?): Boolean {
        if (email != null && email != "") {
            val regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"

            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(email)

            return matcher.matches()
        }

        return true
    }

    fun isUrlValid(url: String?): Boolean {
        if (url != null && url != "") {
            val regex = "/^http(s)?:\\/\\/(www\\.)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$/.test('www.google.com')\n"

            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(url)

            return matcher.matches()
        }

        return false
    }

    fun analyticFormat(str: String): String {
        return str.toLowerCase().replace("[^A-Za-z0-9]+".toRegex(), "_")
    }

    fun checkPlayServices(context: Activity): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(context)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(context, resultCode, 9000).show()
            } else {
                val alert = AlertDialog.Builder(context)
                alert.setTitle(context.resources.getString(R.string.app_name))
                alert.setCancelable(false)
                alert.setMessage(R.string.play_services_warning)
                alert.setPositiveButton("Exit") { _, _ -> System.exit(0) }
            }
            return false
        }
        return true
    }

    fun isPackageExisted(c: Context, targetPackage: String): Boolean {

        val pm = c.packageManager
        try {
            val info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

        return true
    }


    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(format: String): String {
        val calendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat(format)
        return currentDate.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateRangeFormatted(@NonNull start: Date, @NonNull end: Date): String {

        val day1 = SimpleDateFormat("dd").format(start)
        val day2 = SimpleDateFormat("dd").format(end)

        val month1 = SimpleDateFormat("MMM").format(start)
        val month2 = SimpleDateFormat("MMM").format(end)

        val year1 = SimpleDateFormat("yyyy").format(start)
        val year2 = SimpleDateFormat("yyyy").format(end)

        return if (year1 == year2) {
            if (month1 == month2) {
                if (day1 == day2) {
                    SimpleDateFormat("dd MMM yyyy").format(end)
                } else SimpleDateFormat("dd").format(start) + " - " + SimpleDateFormat("dd MMM yyyy").format(end)
            } else {
                SimpleDateFormat("dd MMM").format(start) + " - " + SimpleDateFormat("dd MMM yyyy").format(end)
            }
        } else {
            SimpleDateFormat("dd MMM yyyy").format(start) + " - " + SimpleDateFormat("dd MMM yyyy").format(end)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun checkTime(time: String, endTime: String, expression: Int): Boolean {

        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern)

        try {
            val date1 = sdf.parse(time)
            val date2 = sdf.parse(endTime)

            if (expression == 0)
                return date1.before(date2)
            else if (expression == 1)
                return date1.after(date2)
            else if (expression == 2)
                return date1 == date2
            else if (expression == 3)
                return date1.before(date2) || date1 == date2
            else if (expression == 4)
                return date1.after(date2) || date1 == date2

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }


}
