package com.java.mvp.factory.entity.response.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Route {

    /**
     *
     * @return
     * The legs
     */
    /**
     *
     * @param legs
     * The legs
     */
    @SerializedName("legs")
    @Expose
    var legs: List<Leg> = ArrayList()
    /**
     *
     * @return
     * The overviewPolyline
     */
    /**
     *
     * @param overviewPolyline
     * The overview_polyline
     */
    @SerializedName("overview_polyline")
    @Expose
    var overviewPolyline: OverviewPolyline? = null

}
