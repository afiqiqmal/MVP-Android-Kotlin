package com.java.mvp.factory.entity.response.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Duration {

    /**
     *
     * @return
     * The text
     */
    /**
     *
     * @param text
     * The text
     */
    @SerializedName("text")
    @Expose
    var text: String? = null
    /**
     *
     * @return
     * The value
     */
    /**
     *
     * @param value
     * The value
     */
    @SerializedName("value")
    @Expose
    var value: Int? = null

}