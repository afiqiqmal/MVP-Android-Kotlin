package com.java.mvp.factory.entity.response.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Leg {

    /**
     *
     * @return
     * The distance
     */
    /**
     *
     * @param distance
     * The distance
     */
    @SerializedName("distance")
    @Expose
    var distance: Distance? = null
    /**
     *
     * @return
     * The duration
     */
    /**
     *
     * @param duration
     * The duration
     */
    @SerializedName("duration")
    @Expose
    var duration: Duration? = null

}
