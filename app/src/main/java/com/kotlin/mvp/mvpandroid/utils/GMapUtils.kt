package com.kotlin.mvp.mvpandroid.utils

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.java.mvp.factory.entity.response.map.DirectionPath
import java.util.*
import javax.inject.Inject

/**
 * @author : hafiq on 26/02/2017.
 */

class GMapUtils @Inject
constructor(private val context: Context) {

    var distance: String? = null
        private set
    var time: String? = null
        private set
    private var modeType: Mode? = null

    enum class Mode private constructor(val mapMode: String) {
        DRIVING("driving"),
        BICYCLING("bicycling"),
        WALKING("walking")
    }

    fun setMode(modeType: Mode) {
        this.modeType = modeType
    }

    fun getDirectionsUrl(origin: LatLng, dest: LatLng): LinkedHashMap<String, String> {

        val map = LinkedHashMap<String, String>()
        map["origin"] = origin.latitude.toString() + "," + origin.longitude
        map["destination"] = dest.latitude.toString() + "," + dest.longitude
        map["sensor"] = "false"
        map["units"] = "metric"

        if (modeType != null) {
            map[MODE] = modeType!!.name
        } else {
            map[MODE] = Mode.DRIVING.name
        }

        return map
    }

    fun parse(path: DirectionPath): List<LatLng>? {
        for (i in 0 until path.routes.size) {
            distance = path.routes[i].legs[i].distance?.text
            time = path.routes[i].legs[i].duration?.text
            val encodedString = path.routes[0].overviewPolyline?.points
            return decodePoly(encodedString)
        }

        return null
    }

    private fun decodePoly(encoded: String?): List<LatLng>? {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded?.length
        var lat = 0
        var lng = 0

        while (index < len!!) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5,
                    lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }

    companion object {

        private val MODE = "mode"
    }
}
