package com.java.mvp.factory.entity.response.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class DirectionPath {

    /**
     *
     * @return
     * The routes
     */
    /**
     *
     * @param routes
     * The routes
     */
    @SerializedName("routes")
    @Expose
    var routes: List<Route> = ArrayList()

}